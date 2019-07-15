package com.example.aadil.capstoneproject.ui;

import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aadil.capstoneproject.BuildConfig;
import com.example.aadil.capstoneproject.R;
import com.example.aadil.capstoneproject.database.FavoriteEntry;
import com.example.aadil.capstoneproject.database.MainViewModel;
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

public class MasterListFragment extends Fragment {
    private static final String API_KEY = BuildConfig.APIKEY;
    private Boolean mTwoPane = false;
    private Retrofit retrofit;
    private String query;
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.ItemDecoration mDividerItemDecoration;
    private FavoriteAdapter favoriteAdapter;

    public MasterListFragment() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_master_list, container, false);

        Bundle bundle = getArguments();

        mTwoPane = bundle.getBoolean("mTwoPane");
        query = bundle.getString("query");

        retrofit = RetrofitClientInstance.getRetrofitInstance();
        APIInterface apiInterface = retrofit.create(APIInterface.class);

        Call<ResultList> call = apiInterface.search(API_KEY, query);

        call.enqueue(new Callback<ResultList>() {
            @Override
            public void onResponse(Call<ResultList> call, Response<ResultList> response) {
                if(!response.isSuccessful()){
                    Toast toast = Toast.makeText(getActivity().getApplicationContext(),
                            getResources().getString(R.string.search_error_message), Toast.LENGTH_SHORT);
                    toast.show();
                }

                ArrayList<Result> resultList = response.body().getResultArrayList();

                TextView searchResultTitle = rootView.findViewById(R.id.search_query);
                searchResultTitle.setTypeface(null, Typeface.BOLD_ITALIC);
                searchResultTitle.setText(R.string.search_results_title);
                searchResultTitle.append(" ");
                searchResultTitle.append(query);
                searchResultTitle.append(":");

                mRecyclerView = rootView.findViewById(R.id.rv_results_list);
                mRecyclerView.setHasFixedSize(true);
                mAdapter = new ResultAdapter(resultList, mTwoPane);
                mLayoutManager = new LinearLayoutManager(getContext());
                mRecyclerView.setLayoutManager(mLayoutManager);
                mRecyclerView.setAdapter(mAdapter);

                mDividerItemDecoration = new DividerItemDecoration(mRecyclerView.getContext(),
                        DividerItemDecoration.VERTICAL);
                mRecyclerView.addItemDecoration(mDividerItemDecoration);
            }

            @Override
            public void onFailure(Call<ResultList> call, Throwable t) {
                Log.d(getResources().getString(R.string.results_activity_tag), t.getLocalizedMessage());
            }
        });

        return rootView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.favorites_menu, menu);
        super.onCreateOptionsMenu(menu,inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.favorites:
                setupViewModel();
                break;
            default:
                return super.onOptionsItemSelected(item);
        }

        return true;
    }

    private void setupViewModel() {
        MainViewModel viewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        viewModel.getFavorites().observe(this, new Observer<List<FavoriteEntry>>() {
            @Override
            public void onChanged(@Nullable List<FavoriteEntry> favoriteEntries) {
                favoriteAdapter = new FavoriteAdapter(favoriteEntries, mTwoPane);
                mRecyclerView.setAdapter(favoriteAdapter);
            }
        });
    }
}
