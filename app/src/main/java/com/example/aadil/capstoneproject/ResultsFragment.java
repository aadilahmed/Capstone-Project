package com.example.aadil.capstoneproject;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.aadil.capstoneproject.model.Podcast;

import java.util.ArrayList;

public class ResultsFragment extends Fragment {
    private RecyclerView mResultsRV;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private RecyclerView.ItemDecoration mResultsDividerItemDecoration;

    private Boolean mTwoPane;

    public ResultsFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_results, container, false);

        mResultsRV = rootView.findViewById(R.id.rv_results_list);

        Bundle bundle = getArguments();

        if(bundle != null) {
            mTwoPane = bundle.getBoolean("mTwoPane");
            ArrayList<Podcast> results = bundle.getParcelable("results");

            mResultsRV.setHasFixedSize(true);

            mLayoutManager = new LinearLayoutManager(getContext());
            mResultsRV.setLayoutManager(mLayoutManager);

            mAdapter = new ResultAdapter(results);
            mResultsRV.setAdapter(mAdapter);

            mResultsDividerItemDecoration = new DividerItemDecoration(mResultsRV.getContext(),
                    DividerItemDecoration.VERTICAL);
            mResultsRV.addItemDecoration(mResultsDividerItemDecoration);
        }

        return rootView;
    }
}
