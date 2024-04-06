package com.example.opendata;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.opendata.model.RapArtist;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class ArtistDetailsActivity extends AppCompatActivity {

    public static final String EXTRA_ARTIST = "extra_artist";

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_artist_details);

        TextView artistNameTextView = findViewById(R.id.text_artist_name);
        TextView artistCategoryTextView = findViewById(R.id.text_artist_category);
        TextView artistSummaryTextView = findViewById(R.id.text_artist_summary);
        TextView artistBirthdateTextView = findViewById(R.id.text_artist_birthdate);
        TextView artistYearsActiveTextView = findViewById(R.id.text_artist_years_active);
        TextView artistLocationCityTextView = findViewById(R.id.text_artist_location_city);
        TextView artistLocationNeighborhoodTextView = findViewById(R.id.text_artist_location_neighborhood);

        RapArtist artist = (RapArtist) getIntent().getSerializableExtra(EXTRA_ARTIST);

        if (artist != null) {
            artistNameTextView.setText(underlineText(artist.getName()));

            if (artist.getCategories() != null) {
                artistCategoryTextView.setText(underlineText("Categories : ") + artist.getCategories().toString());
            } else {
                artistCategoryTextView.setText(underlineText("Categories : No details"));
            }

            if (artist.getBioSummary() != null) {
                artistSummaryTextView.setText(underlineText("More about this artist : ") + artist.getBioSummary());
            } else {
                artistSummaryTextView.setText(underlineText("More about this artist : No details"));
            }

            if (artist.getBioBirthdate() != null) {
                artistBirthdateTextView.setText(underlineText("Birth Date : ") + artist.getBioBirthdate());
            } else {
                artistBirthdateTextView.setText(underlineText("Birth Date : No details"));
            }

            if (artist.getBioYearsactivestart() != null) {
                artistYearsActiveTextView.setText(underlineText("Year of activity's start : ") + artist.getBioYearsactivestart());
            } else {
                artistYearsActiveTextView.setText(underlineText("Year of activity's start : No details"));
            }

            if (artist.getLocationCity() != null) {
                artistLocationCityTextView.setText(underlineText("City : ") + artist.getLocationCity());
            } else {
                artistLocationCityTextView.setText(underlineText("City : No details"));
            }

            if (artist.getLocationNeighborhood() != null) {
                artistLocationNeighborhoodTextView.setText("Neighborhood : " + artist.getLocationNeighborhood());
            } else {
                artistLocationNeighborhoodTextView.setText("Neighborhood : No details");
            }
        } else {
            artistNameTextView.setText("No details");
            artistCategoryTextView.setText("No details");
            artistSummaryTextView.setText("No details");
            artistBirthdateTextView.setText("No details");
            artistYearsActiveTextView.setText("No details");
            artistLocationCityTextView.setText("No details");
            artistLocationNeighborhoodTextView.setText("No details");
        }


        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);

        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                if (artist != null && artist.getLocationCoordinates() != null) {
                    double latitude = artist.getLocationCoordinates().getLat();
                    double longitude = artist.getLocationCoordinates().getLon();
                    LatLng artistLocation = new LatLng(latitude, longitude);
                    googleMap.addMarker(new MarkerOptions().position(artistLocation).title(artist.getLocationCity()));
                    googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(artistLocation, 12.0f));
                }
            }
        });
    }

    private SpannableString underlineText(String text) {
        SpannableString spannableString = new SpannableString(text);
        spannableString.setSpan(new UnderlineSpan(), 0, text.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return spannableString;
    }

    public void onBackButtonClick(View view) {
        finish();
    }
}
