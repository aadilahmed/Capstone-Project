package com.example.aadil.capstoneproject;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.aadil.capstoneproject.database.FavoriteEntry;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private Boolean mTwoPane = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(findViewById(R.id.player_fragment) != null) {
            mTwoPane = true;

            if(savedInstanceState != null) {
                PlayerFragment playerFragment = new PlayerFragment();

                Bundle bundle = getIntent().getExtras();
                playerFragment.setArguments(bundle);

                getSupportFragmentManager().beginTransaction()
                        .add(R.id.player_fragment, playerFragment)
                        .commit();
            }
        }
        else {
            mTwoPane = false;
        }

        if (savedInstanceState == null) {
            MasterListFragment masterList = new MasterListFragment();

            Bundle bundle = new Bundle();
            bundle.putBoolean("mTwoPane", mTwoPane);
            masterList.setArguments(bundle);

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.master_list_fragment, masterList)
                    .commit();
        }
    }
}