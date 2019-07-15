package com.example.aadil.capstoneproject.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.aadil.capstoneproject.R;
import com.example.aadil.capstoneproject.database.AppDatabase;
import com.example.aadil.capstoneproject.database.AppExecutors;
import com.example.aadil.capstoneproject.database.FavoriteEntry;
import com.example.aadil.capstoneproject.model.Result;

import java.util.ArrayList;

public class ResultAdapter extends RecyclerView.Adapter<ResultAdapter.ViewHolder> {
    private Context context;
    private Boolean mTwoPane;
    private ArrayList<Result> results;
    private AppDatabase mDb;

    public ResultAdapter(ArrayList<Result> results, Boolean mTwoPane) {
        this.results = results;
        this.mTwoPane = mTwoPane;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView mEpisodeTitleView;
        private TextView mPodcastTitleView;
        private ImageView mImageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mEpisodeTitleView = itemView.findViewById(R.id.episode_title);
            mPodcastTitleView = itemView.findViewById(R.id.podcast_title);
            mImageView = itemView.findViewById(R.id.album_art);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        context = viewGroup.getContext();
        View resultItem = LayoutInflater.from(context)
                .inflate(R.layout.result_item, viewGroup, false);
        return new ResultAdapter.ViewHolder(resultItem);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        final Result podcast = results.get(position);

        String episodeTitle = podcast.getEpisodeTitle();
        String podcastTitle = podcast.getPodcastTitle();
        String image = podcast.getImage();

        viewHolder.mEpisodeTitleView.setText(episodeTitle);
        viewHolder.mPodcastTitleView.setText(podcastTitle);

        Glide.with(context).load(image).into(viewHolder.mImageView);

        Bundle bundle = new Bundle();
        bundle.putParcelable("podcast", podcast);
        final PlayerFragment playerFragment = new PlayerFragment();

        playerFragment.setArguments(bundle);

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mTwoPane) {
                    ((MainActivity) context).getSupportFragmentManager().beginTransaction()
                            .replace(R.id.player_fragment, playerFragment)
                            .addToBackStack(null)
                            .commit();
                }
                else {
                    Intent intent = new Intent(context, PlayerActivity.class);
                    intent.putExtra("podcast", podcast);
                    context.startActivity(intent);
                }
            }
        });

        final String prefFile = context.getResources().getString(R.string.pref_file_key);
        final String favoriteKey = context.getResources().getString(R.string.favorite_key);
        final Boolean notFavorited = context.getResources().getBoolean(R.bool.notFavorited);
        final Boolean isFavorited = context.getResources().getBoolean(R.bool.isFavorited);
        mDb = AppDatabase.getInstance(context);

        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                FavoriteEntry favoriteEntry = mDb.favoriteDao().loadFavoriteById(podcast.getId());

                SharedPreferences sharedPref = context.getSharedPreferences(prefFile, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();

                if(favoriteEntry == null) {
                    editor.putBoolean(favoriteKey + podcast.getId(), notFavorited);
                    editor.apply();
                }
                else {
                    editor.putBoolean(favoriteKey + podcast.getId(), isFavorited);
                    editor.apply();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return results.size();
    }
}
