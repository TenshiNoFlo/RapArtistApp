package com.example.opendata;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.opendata.adapter.ArtistListAdapter;
import com.example.opendata.manager.ArtistListController;
import com.example.opendata.manager.IRapArtistDataManagerCallBack;
import com.example.opendata.model.RapArtist;
import com.example.opendata.model.ApiResponse;
import com.example.opendata.service.RapArtistService;
import com.example.opendata.service.RetrofitClientInstance;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ArtistListActivity extends AppCompatActivity implements IRapArtistDataManagerCallBack {

    private ListView listView;
    private ArtistListAdapter artistListAdapter;
    private List<RapArtist> artistList = new ArrayList<>();
    private ArtistListController artistListController = new ArtistListController(this);
    private RapArtistService artistService;
    private boolean isLoading = false;

    private int currentPage = 1;
    private boolean isFilteredByFavorites = false;

    @Override
    protected void onStop() {
        super.onStop();
        saveRapArtistsToLocal(artistList);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        loadRapArtistsFromLocalIfNeeded();
    }

    private void loadRapArtistsFromLocalIfNeeded() {
        if (artistList.isEmpty()) {
            loadRapArtistsFromLocal(new IRapArtistDataManagerCallBack() {
                @Override
                public void getTimeResponseSuccess(RapArtist rapArtist) {
                    artistList.add(rapArtist);
                    artistListAdapter.notifyDataSetChanged();
                }

                @Override
                public void getTimeResponseError(String message) {
                }
            });
        }
    }

    private void saveRapArtistsToLocal(List<RapArtist> rapArtists) {
        try {
            FileOutputStream fileOutputStream = openFileOutput("rap_artists.dat", Context.MODE_PRIVATE);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(rapArtists);
            objectOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadRapArtistsFromLocal(IRapArtistDataManagerCallBack callBack) {
        try {
            FileInputStream fileInputStream = openFileInput("rap_artists.dat");
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            List<RapArtist> rapArtists = (List<RapArtist>) objectInputStream.readObject();
            objectInputStream.close();

            for (RapArtist rapArtist : rapArtists) {
                callBack.getTimeResponseSuccess(rapArtist);
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            callBack.getTimeResponseError("Error: " + e.getMessage());
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_artist_list);

        Button btnFilterFavorite = findViewById(R.id.btn_filter_favorite);

        artistService = RetrofitClientInstance.getRetrofitInstance().create(RapArtistService.class);

        listView = findViewById(R.id.listview_artist);
        artistListAdapter = new ArtistListAdapter(this, artistList);
        listView.setAdapter(artistListAdapter);

        EditText editTextSearch = findViewById(R.id.editText_search);
        editTextSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filterArtistList(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        listView.setOnScrollListener(new ListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (!isLoading && (firstVisibleItem + visibleItemCount == totalItemCount)) {
                    loadMoreData();
                }
            }
        });

        btnFilterFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filterFavoriteArtists();
            }
        });

        getRapArtists();
    }

    private void getRapArtists() {
        if(!isFilteredByFavorites) {
            isLoading = true;
            artistListController.fetchRapArtists(currentPage, this);
        }
    }

    private void loadMoreData() {
        isLoading = true;
        artistListController.fetchRapArtists(currentPage + 1, this);
    }

    private void filterArtistList(String searchText) {
        Call<ApiResponse> call = artistService.searchRapArtistsByName(searchText);
        call.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                if (response.isSuccessful()) {
                    List<RapArtist> allArtists = response.body().getResults();
                    List<RapArtist> filteredListSearch = new ArrayList<>();
                    for (RapArtist artist : allArtists) {
                        if (artist.getName().toLowerCase().contains(searchText.toLowerCase())) {
                            filteredListSearch.add(artist);
                        }
                    }
                    artistList.clear();
                    artistList.addAll(filteredListSearch);
                    artistListAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
            }
        });
    }



    @Override
    public void getTimeResponseSuccess(RapArtist rapArtist) {
        artistList.add(rapArtist);
        artistListAdapter.notifyDataSetChanged();
        isLoading = false;
    }

    @Override
    public void getTimeResponseError(String message) {
        isLoading = false;
    }

    private void filterFavoriteArtists() {
        List<RapArtist> favoriteArtists = new ArrayList<>();
        for (RapArtist artist : artistList) {
            if (artist.isFavorite()) {
                favoriteArtists.add(artist);
            }
        }
        artistListAdapter.clear();
        artistListAdapter.addAll(favoriteArtists);
        artistListAdapter.notifyDataSetChanged();

        isFilteredByFavorites = true;
    }
}
