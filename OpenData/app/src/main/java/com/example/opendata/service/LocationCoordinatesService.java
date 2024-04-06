package com.example.opendata.service;

import retrofit2.Call;
import retrofit2.http.GET;

public interface LocationCoordinatesService {

    @GET("{}")
    Call<LocationCoordinatesService> getLocationCoordinates();
}
