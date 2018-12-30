package com.example.aadil.capstoneproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class ResultsActivity extends AppCompatActivity {
    private Boolean mTwoPane = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        /*if(findViewById(R.id.step_detail_fragment) != null) {
            mTwoPane = true;

            if(savedInstanceState != null) {
                StepDetailFragment stepFragment = new StepDetailFragment();

                Bundle bundle = getIntent().getExtras();
                stepFragment.setArguments(bundle);

                getSupportFragmentManager().beginTransaction()
                        .add(R.id.step_detail_fragment, stepFragment)
                        .commit();
            }
        }
        else {
            mTwoPane = false;
        }*/

        if (savedInstanceState == null) {
            MasterListFragment resultsList = new MasterListFragment();

            Bundle bundle = getIntent().getExtras();
            if(bundle != null) {
                bundle.putBoolean("mTwoPane", mTwoPane);
                resultsList.setArguments(bundle);

                getSupportFragmentManager().beginTransaction()
                        .add(R.id.results_fragment, resultsList)
                        .commit();
            }
        }
    }
}
