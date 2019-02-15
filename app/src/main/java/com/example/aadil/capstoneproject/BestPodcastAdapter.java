package com.example.aadil.capstoneproject;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.aadil.capstoneproject.model.Channel;

import java.util.ArrayList;

public class BestPodcastAdapter extends RecyclerView.Adapter<BestPodcastAdapter.ViewHolder> {
    ArrayList<Channel> channels;
    Context context;

    BestPodcastAdapter(ArrayList<Channel> channels) {
        this.channels = channels;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView mImageView;
        private TextView mPodcastTitleView;
        private TextView mPublisherView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mPodcastTitleView = itemView.findViewById(R.id.best_podcast_title);
            mPublisherView = itemView.findViewById(R.id.best_podcast_publisher);
            mImageView = itemView.findViewById(R.id.best_podcast_art);
        }
    }

    @NonNull
    @Override
    public BestPodcastAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        context = viewGroup.getContext();
        View podcastItem = LayoutInflater.from(context)
                .inflate(R.layout.best_podcast_item, viewGroup, false);
        return new BestPodcastAdapter.ViewHolder(podcastItem);
    }

    @Override
    public void onBindViewHolder(@NonNull BestPodcastAdapter.ViewHolder viewHolder, int i) {
        final Channel channel = channels.get(i);

        String id = channel.getId();
        String podcastTitle = channel.getTitle();
        String publisher = channel.getPublisher();
        String image = channel.getImage();

        viewHolder.mPodcastTitleView.setText(podcastTitle);
        viewHolder.mPublisherView.setText(publisher);

        Glide.with(context).load(image).into(viewHolder.mImageView);

        /*viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, PlayerActivity.class);
                intent.putExtra("channel", channel);
                context.startActivity(intent);
            }
        });*/
    }

    @Override
    public int getItemCount() {
        return channels.size();
    }
}
