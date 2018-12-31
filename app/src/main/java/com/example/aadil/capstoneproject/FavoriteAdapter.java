package com.example.aadil.capstoneproject;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.aadil.capstoneproject.database.FavoriteEntry;
import com.example.aadil.capstoneproject.model.Result;

import java.util.List;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.ViewHolder> {
    private Context context;
    private Boolean mTwoPane;
    private List<FavoriteEntry> results;

    public FavoriteAdapter(List<FavoriteEntry> results, Boolean mTwoPane) {
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
        return new FavoriteAdapter.ViewHolder(resultItem);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        final FavoriteEntry favorite = results.get(position);

        String id = favorite.getId();
        String url = favorite.getUrl();
        String episodeTitle = favorite.getEpisodeTitle();
        String podcastTitle = favorite.getPodcastTitle();
        String image = favorite.getImage();

        viewHolder.mEpisodeTitleView.setText(episodeTitle);
        viewHolder.mPodcastTitleView.setText(podcastTitle);

        Glide.with(context).load(image).into(viewHolder.mImageView);

        final Result podcast = new Result(id, url, episodeTitle, podcastTitle, image);

        Bundle bundle = new Bundle();
        bundle.putParcelable("podcast", podcast);
        final PlayerFragment playerFragment = new PlayerFragment();

        playerFragment.setArguments(bundle);

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mTwoPane) {
                    ((ResultsActivity) context).getSupportFragmentManager().beginTransaction()
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
    }

    @Override
    public int getItemCount() {
        return results.size();
    }
}
