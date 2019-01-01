package com.example.aadil.capstoneproject.widget;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.example.aadil.capstoneproject.R;
import com.example.aadil.capstoneproject.provider.FavoriteContract;

import static com.example.aadil.capstoneproject.provider.FavoriteContract.BASE_CONTENT_URI;
import static com.example.aadil.capstoneproject.provider.FavoriteContract.PATH_FAVORITES;

public class FavoriteWidgetRemoteViewsService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new FavoritesRemoteViewsFactory(this.getApplicationContext(), intent);
    }
}

class FavoritesRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {
    private Context context;
    Cursor cursor;
    private Intent intent;
    private int appWidgetId;

    public FavoritesRemoteViewsFactory (Context context, Intent intent) {
        this.context = context;
        this.intent = intent;
        appWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
                AppWidgetManager.INVALID_APPWIDGET_ID);
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {
        Uri FAVORITE_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_FAVORITES).build();

        if (cursor != null){
            cursor.close();
        }

        cursor = context.getContentResolver().query(
                FAVORITE_URI,
                null,
                null,
                null,
                null
        );

    }

    @Override
    public void onDestroy() {
        cursor.close();
    }

    @Override
    public int getCount() {
        if (cursor == null) {
            return 0;
        }
        return cursor.getCount();
    }

    @Override
    public RemoteViews getViewAt(int i) {
        if (cursor == null || cursor.getCount() == 0) {
            return null;
        }
        cursor.moveToPosition(i);

        int urlIndex = cursor.getColumnIndex(FavoriteContract.FavoriteEntry.COLUMN_URL);
        int episodeTitleIndex = cursor.getColumnIndex(FavoriteContract.FavoriteEntry.COLUMN_EPISODE_TITLE);
        int podcastTitleIndex = cursor.getColumnIndex(FavoriteContract.FavoriteEntry.COLUMN_PODCAST_TITLE);
        int imageIndex = cursor.getColumnIndex(FavoriteContract.FavoriteEntry.COLUMN_IMAGE);

        String url = cursor.getString(urlIndex);
        String episodeTitle = cursor.getString(episodeTitleIndex);
        String podcastTitle = cursor.getString(podcastTitleIndex);
        String image = cursor.getString(imageIndex);

        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.favorite_widget_item);

        views.setTextViewText(R.id.favorite_widget_row, episodeTitle);

        Intent intent = new Intent();
        views.setOnClickFillInIntent(R.id.favorite_widget_row, intent);

        return views;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }
}
