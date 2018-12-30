package com.example.aadil.capstoneproject;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.aadil.capstoneproject.model.Result;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

public class PlayerFragment extends Fragment {
    private String audio_url;
    private String episodeTitle;
    private String podcastTitle;
    private String image;

    private SimpleExoPlayer simpleExoPlayer;
    private SimpleExoPlayerView mPlayerView;
    private Boolean playWhenReady = true;
    private int currentWindow = 0;
    private long position = 0;

    public PlayerFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        if(savedInstanceState != null) {
            position = savedInstanceState.getLong("position");
            playWhenReady = savedInstanceState.getBoolean("playWhenReady");
        }

        View rootView = inflater.inflate(R.layout.fragment_player, container, false);
        Bundle bundle = getArguments();

        Result podcast = bundle.getParcelable("podcast");

        audio_url = podcast.getUrl();
        episodeTitle = podcast.getEpisodeTitle();
        podcastTitle = podcast.getPodcastTitle();
        image = podcast.getImage();

        mPlayerView = rootView.findViewById(R.id.player_view);

        TextView playerTitle = rootView.findViewById(R.id.player_title);
        playerTitle.setText(episodeTitle);

        return rootView;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putLong("position", simpleExoPlayer.getCurrentPosition());
        outState.putBoolean("playWhenReady", simpleExoPlayer.getPlayWhenReady());
    }

    private MediaSource buildMediaSource(Uri uri) {
        return new ExtractorMediaSource.Factory(
                new DefaultHttpDataSourceFactory("exoplayer-codelab")).
                createMediaSource(uri);
    }

    @Override
    public void onStart() {
        super.onStart();
        if(Util.SDK_INT > 23) {
            initializePlayer();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if((Util.SDK_INT <= 23 || simpleExoPlayer == null)) {
            initializePlayer();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if(Util.SDK_INT <= 23) {
            releasePlayer();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if(Util.SDK_INT >= 24) {
            releasePlayer();
        }
    }

    private void initializePlayer() {
        simpleExoPlayer = ExoPlayerFactory.newSimpleInstance(
                new DefaultRenderersFactory(getActivity()), new DefaultTrackSelector(), new DefaultLoadControl());

        mPlayerView.setPlayer(simpleExoPlayer);
        simpleExoPlayer.setPlayWhenReady(playWhenReady);
        simpleExoPlayer.seekTo(currentWindow, position);

        Uri uri = Uri.parse(audio_url);
        MediaSource mediaSource = buildMediaSource(uri);
        simpleExoPlayer.prepare(mediaSource, true, false);
    }

    private void releasePlayer() {
        if (simpleExoPlayer != null) {
            position = simpleExoPlayer.getCurrentPosition();
            currentWindow = simpleExoPlayer.getCurrentWindowIndex();
            playWhenReady = simpleExoPlayer.getPlayWhenReady();
            simpleExoPlayer.release();
            simpleExoPlayer = null;
        }
    }
}