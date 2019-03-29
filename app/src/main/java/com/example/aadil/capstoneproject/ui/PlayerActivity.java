package com.example.aadil.capstoneproject.ui;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.aadil.capstoneproject.R;

public class PlayerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);

        if(savedInstanceState == null) {
            PlayerFragment playerFragment = new PlayerFragment();

            Bundle bundle = getIntent().getExtras();
            playerFragment.setArguments(bundle);

            FragmentManager fragmentManager = getSupportFragmentManager();

            fragmentManager.beginTransaction().add(R.id.player_fragment, playerFragment).commit();
        }
    }
}
