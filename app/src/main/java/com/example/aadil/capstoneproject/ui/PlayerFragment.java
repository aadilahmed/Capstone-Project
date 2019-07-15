package com.example.aadil.capstoneproject.ui;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.aadil.capstoneproject.R;
import com.example.aadil.capstoneproject.database.AppDatabase;
import com.example.aadil.capstoneproject.database.AppExecutors;
import com.example.aadil.capstoneproject.database.FavoriteEntry;
import com.example.aadil.capstoneproject.model.Result;
import com.example.aadil.capstoneproject.provider.FavoriteContract;
import com.example.aadil.capstoneproject.widget.UpdateFavoritesService;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.exoplayer2.util.Util;


public class PlayerFragment extends Fragment {
    private String id;
    private String audio_url;
    private String episodeTitle;
    private String podcastTitle;
    private String image;

    private SimpleExoPlayer simpleExoPlayer;
    private PlayerView mPlayerView;
    private Boolean playWhenReady = true;
    private int currentWindow = 0;
    private long position = 0;

    private AppDatabase mDb;
    private final static String prefFile = "preferenceFile";
    private Boolean podcastIsFavorited;

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

        mDb = AppDatabase.getInstance(getActivity().getApplicationContext());

        View rootView = inflater.inflate(R.layout.fragment_player, container, false);
        Bundle bundle = getArguments();

        Result podcast = bundle.getParcelable("podcast");

        id = podcast.getId();
        audio_url = podcast.getUrl();
        episodeTitle = podcast.getEpisodeTitle();
        podcastTitle = podcast.getPodcastTitle();
        image = podcast.getImage();

        mPlayerView = rootView.findViewById(R.id.player_view);

        TextView playerTitle = rootView.findViewById(R.id.player_title);
        playerTitle.setText(episodeTitle);

        SharedPreferences sharedPref = getContext().getSharedPreferences(getString(R.string.pref_file_key),
                Context.MODE_PRIVATE);
        Boolean defaultVal = getResources().getBoolean(R.bool.notFavorited);
        podcastIsFavorited = sharedPref.getBoolean(getString(R.string.favorite_key) + id, defaultVal);

        Context context = getContext();

        ToggleButton toggleButton = rootView.findViewById(R.id.favorite_button);
        toggleButton.setChecked(podcastIsFavorited);

        onFavoritesButtonClicked(podcast, context, toggleButton);

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

    public void onFavoritesButtonClicked(final Result podcast, final Context context, final ToggleButton button) {
        final String id = podcast.getId();
        final String url = podcast.getUrl();
        final String episodeTitle = podcast.getEpisodeTitle();
        final String podcastTitle = podcast.getPodcastTitle();
        final String image = podcast.getImage();

        button.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    final FavoriteEntry favoriteEntry = new FavoriteEntry(id, url, episodeTitle, podcastTitle, image);
                    AppExecutors.getInstance().diskIO().execute(new Runnable() {
                        @Override
                        public void run() {
                            mDb.favoriteDao().insertFavorite(favoriteEntry);
                        }
                    });
                    updateWidgetList(context, podcast);
                } else {
                    AppExecutors.getInstance().diskIO().execute(new Runnable() {
                        @Override
                        public void run() {
                            FavoriteEntry favoriteEntry = mDb.favoriteDao().loadFavoriteById(id);
                            mDb.favoriteDao().deleteFavorite(favoriteEntry);
                        }
                    });

                    Uri uri = FavoriteContract.FavoriteEntry.CONTENT_URI;
                    uri = uri.buildUpon().appendPath(podcast.getId()).build();
                    getContext().getContentResolver().delete(uri,
                            FavoriteContract.FavoriteEntry._ID + " = ?", new String[]{podcast.getId()});
                    UpdateFavoritesService.startActionUpdateFavoriteWidgets(context);
                }

                SharedPreferences sharedPref = context.getSharedPreferences(prefFile, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putBoolean(getString(R.string.favorite_key) + id, isChecked);
                editor.apply();
            }
        });
    }

    private void updateWidgetList(Context context, Result podcast) {
        Uri uri = FavoriteContract.FavoriteEntry.CONTENT_URI;
        uri = uri.buildUpon().appendPath(podcast.getId()).build();
        getContext().getContentResolver().delete(uri,
                FavoriteContract.FavoriteEntry._ID + " = ?", new String[]{podcast.getId()});

        ContentValues contentValues = new ContentValues();
        contentValues.put(FavoriteContract.FavoriteEntry._ID, podcast.getId());
        contentValues.put(FavoriteContract.FavoriteEntry.COLUMN_URL, podcast.getUrl());
        contentValues.put(FavoriteContract.FavoriteEntry.COLUMN_PODCAST_TITLE, podcast.getPodcastTitle());
        contentValues.put(FavoriteContract.FavoriteEntry.COLUMN_EPISODE_TITLE, podcast.getEpisodeTitle());
        contentValues.put(FavoriteContract.FavoriteEntry.COLUMN_IMAGE, podcast.getImage());
        context.getContentResolver().insert(FavoriteContract.FavoriteEntry.CONTENT_URI, contentValues);
        UpdateFavoritesService.startActionUpdateFavoriteWidgets(context);
    }
}