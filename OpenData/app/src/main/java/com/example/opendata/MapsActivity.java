package com.example.opendata;

import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.opendata.model.RapArtist;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    public static final String EXTRA_ARTIST = "extra_artist";

    private GoogleMap mMap;
    private RapArtist artist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        assert mapFragment != null;
        mapFragment.getMapAsync(this);

        artist = (RapArtist) getIntent().getSerializableExtra(EXTRA_ARTIST);
        Log.e("Name_Paste_Test",artist.getName());
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        if (artist != null && artist.getLocationCoordinates() != null) {
            double latitude = artist.getLocationCoordinates().getLat();
            double longitude = artist.getLocationCoordinates().getLon();
            Log.e("CoordPaste",latitude + " " + longitude);
            LatLng artistLocation = new LatLng(latitude, longitude);
            mMap.addMarker(new MarkerOptions().position(artistLocation).title(artist.getLocationCity()));
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(artistLocation, 17));
        }
    }
}
