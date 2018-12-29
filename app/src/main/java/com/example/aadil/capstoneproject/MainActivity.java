package com.example.aadil.capstoneproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.aadil.capstoneproject.utils.APIInterface;
import com.example.aadil.capstoneproject.utils.RetrofitClientInstance;

import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {
    private static final String API_KEY = BuildConfig.APIKEY;
    private Retrofit retrofit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        retrofit = RetrofitClientInstance.getRetrofitInstance();

        APIInterface apiInterface = retrofit.create(APIInterface.class);


    }
}
