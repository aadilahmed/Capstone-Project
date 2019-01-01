package com.example.aadil.capstoneproject.widget;

import android.app.IntentService;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;

public class UpdateFavoritesService extends IntentService {
    public static final String ACTION_UPDATE_FAVORITE_WIDGETS =
            "com.example.aadil.capstoneproject.action.update_favorite_widgets";

    public UpdateFavoritesService() {
        super("UpdateFavoritesService");
    }

    public static void startActionUpdateFavoriteWidgets(Context context) {
        Intent intent = new Intent(context, UpdateFavoritesService.class);
        intent.setAction(ACTION_UPDATE_FAVORITE_WIDGETS);
        context.startService(intent);
    }

    private void handleActionUpdateFavoriteWidgets() {
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        int[] appWidgetIds = appWidgetManager
                .getAppWidgetIds(new ComponentName(this, FavoriteWidgetProvider.class));

        FavoriteWidgetProvider.updateFavoriteWidgets(this, appWidgetManager, appWidgetIds);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_UPDATE_FAVORITE_WIDGETS.equals(action)) {
                handleActionUpdateFavoriteWidgets();
            }
        }
    }
}
