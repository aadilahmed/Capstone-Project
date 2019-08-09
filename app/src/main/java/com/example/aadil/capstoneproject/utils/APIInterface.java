package com.example.aadil.capstoneproject.utils;

import com.example.aadil.capstoneproject.model.ChannelList;
import com.example.aadil.capstoneproject.model.ResultList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

public interface APIInterface {
    @GET("api/v2/search")
    Call<ResultList> search(@Header("X-ListenAPI-Key") String xMashapeKey,
                            @Query(value = "q") String query);

    @GET("api/v2/best_podcasts")
    Call<ChannelList> best_podcasts(@Header("X-ListenAPI-Key") String xMashapeKey);

}