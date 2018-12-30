package com.example.aadil.capstoneproject;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aadil.capstoneproject.model.Podcast;
import com.example.aadil.capstoneproject.model.Result;
import com.example.aadil.capstoneproject.model.ResultList;
import com.example.aadil.capstoneproject.utils.APIInterface;
import com.example.aadil.capstoneproject.utils.RetrofitClientInstance;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {
    private static final String API_KEY = BuildConfig.APIKEY;
    private Boolean mTwoPane = false;
    private Retrofit retrofit;
    private String query;
    private static final String TAG = "MainActivity";
    RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.ItemDecoration mDividerItemDecoration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //SearchView searchBar = findViewById(R.id.podcast_search_bar);

        retrofit = RetrofitClientInstance.getRetrofitInstance();
        APIInterface apiInterface = retrofit.create(APIInterface.class);

        Call<ResultList> call = apiInterface.search(API_KEY, "Giant Bomb");

        Log.wtf("URL Called", call.request().url() + "");

        call.enqueue(new Callback<ResultList>() {
            @Override
            public void onResponse(Call<ResultList> call, Response<ResultList> response) {
                if(!response.isSuccessful()){
                    Toast toast = Toast.makeText(getApplicationContext(), "CALL NOT WORKING", Toast.LENGTH_SHORT);
                    toast.show();
                }

                ArrayList<Result> resultList = response.body().getResultArrayList();

                mRecyclerView = findViewById(R.id.rv_results_list);
                mRecyclerView.setHasFixedSize(true);
                mAdapter = new ResultAdapter(resultList);
                mLayoutManager = new LinearLayoutManager(MainActivity.this);
                mRecyclerView.setLayoutManager(mLayoutManager);
                mRecyclerView.setAdapter(mAdapter);

                mDividerItemDecoration = new DividerItemDecoration(mRecyclerView.getContext(),
                        DividerItemDecoration.VERTICAL);
                mRecyclerView.addItemDecoration(mDividerItemDecoration);
            }

            @Override
            public void onFailure(Call<ResultList> call, Throwable t) {
                Log.d(TAG, t.getLocalizedMessage());
            }
        });
    }
}