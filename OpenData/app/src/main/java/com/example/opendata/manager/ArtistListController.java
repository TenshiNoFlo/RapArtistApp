package com.example.opendata.manager;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

import com.example.opendata.adapter.ArtistListAdapter;
import com.example.opendata.manager.IRapArtistDataManagerCallBack;
import com.example.opendata.model.ApiResponse;
import com.example.opendata.model.RapArtist;

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

public class ArtistListController {

    private final ApiManager apiManager;
    private final Context context;

    public ArtistListController(Context context) {
        this.context = context;
        apiManager = ApiManager.getInstance(context);
    }

    public void fetchRapArtists(int page, IRapArtistDataManagerCallBack callBack) {
        if (!isNetworkAvailable()) {
            Toast.makeText(context, "Pas de connexion réseau", Toast.LENGTH_SHORT).show();
            loadRapArtistsFromLocal(callBack);
            return;
        }

        getRapArtists(page, callBack);
    }

    public void getAllRapArtists(IRapArtistDataManagerCallBack callBack) {
        fetchRapArtists(1, callBack);
    }

    public void loadMoreRapArtists(int page, IRapArtistDataManagerCallBack callBack) {
        fetchRapArtists(page, callBack);
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    private void getRapArtists(int page, IRapArtistDataManagerCallBack callBack) {
        int limit = 20;
        Call<ApiResponse> callAllRapArtists = apiManager.getRapArtistService().getRapArtistsWithPagination(page, limit);
        callAllRapArtists.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                if (response.isSuccessful()) {
                    ApiResponse apiResponse = response.body();
                    if (apiResponse != null && apiResponse.getResults() != null) {
                        List<RapArtist> artists = apiResponse.getResults();
                        for (RapArtist rapArtist : artists) {
                            callBack.getTimeResponseSuccess(rapArtist);
                        }
                        saveRapArtistsToLocal(artists);
                    } else {
                        callBack.getTimeResponseError("No rap artists found.");
                    }
                } else {
                    callBack.getTimeResponseError("Error: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                callBack.getTimeResponseError("Error: " + t.getLocalizedMessage());
            }
        });
    }

    private void saveRapArtistsToLocal(List<RapArtist> rapArtists) {
        try {
            FileOutputStream fileOutputStream = context.openFileOutput("rap_artists.dat", Context.MODE_PRIVATE);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(rapArtists);
            objectOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadRapArtistsFromLocal(IRapArtistDataManagerCallBack callBack) {
        try {
            FileInputStream fileInputStream = context.openFileInput("rap_artists.dat");
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
}
