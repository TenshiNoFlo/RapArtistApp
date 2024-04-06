package com.example.opendata.service;

import com.example.opendata.model.ApiResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RapArtistService {

    @GET("rapworld/records?where=name%3D\"Booba\"")
    Call<ApiResponse> getRapArtist();

    @GET("rapworld/records?limit=100")
    Call<ApiResponse> getAllRapArtists();

    @GET("rapworld/records")
    Call<ApiResponse> getRapArtistsWithPagination(@Query("offset") int offset, @Query("limit") int limit);

    @GET("rapworld/records")
    Call<ApiResponse> searchRapArtistsByName(@Query("where") String whereClause);
}
