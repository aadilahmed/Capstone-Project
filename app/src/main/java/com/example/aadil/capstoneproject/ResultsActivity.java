package com.example.aadil.capstoneproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class ResultsActivity extends AppCompatActivity {
    private Boolean mTwoPane = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

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

            Bundle bundle = getIntent().getExtras();
            bundle.putBoolean("mTwoPane", mTwoPane);
            masterList.setArguments(bundle);

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.master_list_fragment, masterList)
                    .commit();
        }
    }
}
