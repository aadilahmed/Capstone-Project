package com.example.aadil.capstoneproject;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class MasterListFragment extends Fragment {
    private RecyclerView mResultsRV;

    public MasterListFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_master_list, container, false);

        //mResultsRV = rootView.findViewById(R.id.rv_result_list);

        Bundle bundle = getArguments();

        return rootView;
    }
}
