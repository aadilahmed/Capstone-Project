package com.example.aadil.capstoneproject.utils;

import com.example.aadil.capstoneproject.model.Podcast;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

public interface APIInterface {
    @GET("api/v1/search")
    Call<List<Podcast>> search(@Header("X-Mashape-Key") String xMashapeKey,
                        @Query(value = "q", encoded = true) String query);
}
