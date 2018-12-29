package com.example.aadil.capstoneproject;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.aadil.capstoneproject.model.Podcast;

import java.util.ArrayList;

public class ResultAdapter extends RecyclerView.Adapter<ResultAdapter.ViewHolder> {
    private Context context;
    private ArrayList<Podcast> results;

    public ResultAdapter(ArrayList<Podcast> results) {
        this.results = results;
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
        final Podcast podcast = results.get(position);

        String episodeTitle = podcast.getEpisodeTitle();
        String podcastTitle = podcast.getPodcastTitle();
        String image = podcast.getImage();

        viewHolder.mEpisodeTitleView.setText(episodeTitle);
        viewHolder.mPodcastTitleView.setText(podcastTitle);

        Glide.with(context).load(image).into(viewHolder.mImageView);

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, PlayerActivity.class);
                intent.putExtra("podcast", podcast);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        if(results == null) {
            return 0;
        }
        return results.size();
    }
}
