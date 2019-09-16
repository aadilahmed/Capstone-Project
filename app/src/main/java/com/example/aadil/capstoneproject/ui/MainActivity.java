package com.example.aadil.capstoneproject.ui;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;

import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.CursorLoader;
import androidx.loader.content.Loader;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aadil.capstoneproject.BuildConfig;
import com.example.aadil.capstoneproject.R;
import com.example.aadil.capstoneproject.model.Channel;
import com.example.aadil.capstoneproject.model.ChannelList;
import com.example.aadil.capstoneproject.utils.APIInterface;
import com.example.aadil.capstoneproject.utils.RetrofitClientInstance;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.analytics.FirebaseAnalytics;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import static com.example.aadil.capstoneproject.provider.FavoriteContract.BASE_CONTENT_URI;
import static com.example.aadil.capstoneproject.provider.FavoriteContract.PATH_FAVORITES;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {
    private static final String API_KEY = BuildConfig.APIKEY;
    private FirebaseAnalytics mFirebaseAnalytics;
    private Retrofit retrofit;
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView.Adapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNav = findViewById(R.id.bottom_nav);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

        MobileAds.initialize(this, getResources().getString(R.string.app_ad_id));

        AdView mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();
        mAdView.loadAd(adRequest);

        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        mFirebaseAnalytics.setCurrentScreen(this,
                getResources().getString(R.string.analytics_screen_name), null);

        SearchView searchView = findViewById(R.id.search_podcasts);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                Intent intent = new Intent(MainActivity.this, ResultsActivity.class);
                intent.putExtra("query", s);
                startActivity(intent);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });

        retrofit = RetrofitClientInstance.getRetrofitInstance();
        APIInterface apiInterface = retrofit.create(APIInterface.class);

        Call<ChannelList> call = apiInterface.best_podcasts(API_KEY);

        call.enqueue(new Callback<ChannelList>() {
            @Override
            public void onResponse(Call<ChannelList> call, Response<ChannelList> response) {
                if(!response.isSuccessful()){
                }


                ArrayList<Channel> channelList = response.body().getChannelArrayList();

                mRecyclerView = findViewById(R.id.rv_best_podcasts);
                mRecyclerView.setHasFixedSize(true);
                mAdapter = new BestPodcastAdapter(channelList);
                mLayoutManager = new LinearLayoutManager(MainActivity.this,
                        LinearLayoutManager.HORIZONTAL, false);
                mRecyclerView.setLayoutManager(mLayoutManager);
                mRecyclerView.setAdapter(mAdapter);
            }

            @Override
            public void onFailure(Call<ChannelList> call, Throwable t) {
                Log.d(getResources().getString(R.string.results_activity_tag), t.getLocalizedMessage());
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.favorites_menu, menu);

        return true;
    }

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int i, @Nullable Bundle bundle) {
        Uri FAVORITE_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_FAVORITES).build();
        return new CursorLoader(this, FAVORITE_URI, null,
                null, null, null);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor cursor) {

    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {

    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                    Intent intent;
                    switch(menuItem.getItemId()) {
                        case R.id.home:
                            intent = new Intent(MainActivity.this, ResultsActivity.class);
                            startActivity(intent);
                            break;

                        case R.id.search:
                            intent = new Intent(MainActivity.this, MainActivity.class);
                            startActivity(intent);
                            break;

                        case R.id.favorites:
                            intent = new Intent(MainActivity.this, MainActivity.class);
                            startActivity(intent);
                            break;
                    }

                    return true;
                }
            };
}