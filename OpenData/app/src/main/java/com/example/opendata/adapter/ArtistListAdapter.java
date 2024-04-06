package com.example.opendata.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.opendata.ArtistDetailsActivity;
import com.example.opendata.R;
import com.example.opendata.model.RapArtist;

import java.util.List;

public class ArtistListAdapter extends ArrayAdapter<RapArtist> {

    private Context context;
    private List<RapArtist> artists;
    private OnFavoriteStateChangedListener callback;

    public ArtistListAdapter(Context context, List<RapArtist> artists) {
        super(context, 0, artists);
        this.context = context;
        this.artists = artists;
    }

    public void setOnFavoriteStateChangedListener(OnFavoriteStateChangedListener listener) {
        this.callback = listener;
    }

    public interface OnFavoriteStateChangedListener {
        void onFavoriteStateChanged(RapArtist artist);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItem = convertView;
        if (listItem == null)
            listItem = LayoutInflater.from(context).inflate(R.layout.list_item_artist, parent, false);

        RapArtist currentArtist = artists.get(position);

        TextView artistName = listItem.findViewById(R.id.text_artist_name);
        TextView artistSummary = listItem.findViewById(R.id.text_artist_summary);
        artistName.setText(currentArtist.getName());
        artistSummary.setText(currentArtist.getBioSummary());

        if (currentArtist.isFavorite()) {
            artistName.setText(artistName.getText() + " ⭐");
        } else {
            artistName.setText(artistName.getText());
        }

        listItem.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                currentArtist.setFavorite(!currentArtist.isFavorite());
                notifyDataSetChanged();
                Toast.makeText(context, "Favorite", Toast.LENGTH_SHORT).show();

                if (callback != null) {
                    callback.onFavoriteStateChanged(currentArtist);
                }

                return true;
            }
        });

        listItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ArtistDetailsActivity.class);
                intent.putExtra(ArtistDetailsActivity.EXTRA_ARTIST, currentArtist);
                context.startActivity(intent);
            }
        });

        return listItem;
    }
}
