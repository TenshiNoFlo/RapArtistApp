package com.example.opendata.manager;

import com.example.opendata.model.RapArtist;

public interface IRapArtistDataManagerCallBack {
    void getTimeResponseSuccess(RapArtist rapArtist);
    void getTimeResponseError(String message);
}
