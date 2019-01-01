package com.example.aadil.capstoneproject.provider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.Arrays;

public class FavoriteContentProvider extends ContentProvider {
    public static final int FAVORITES = 100;
    public static final int FAVORITE_WITH_ID = 101;

    private static final UriMatcher sUriMatcher = buildUriMatcher();

    public static UriMatcher buildUriMatcher() {
        UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(FavoriteContract.AUTHORITY, FavoriteContract.PATH_FAVORITES, FAVORITES);
        uriMatcher.addURI(FavoriteContract.AUTHORITY, FavoriteContract.PATH_FAVORITES + "/*",
                FAVORITE_WITH_ID);
        return uriMatcher;
    }

    private FavoriteDbHelper mFavoriteDbHelper;

    @Override
    public boolean onCreate() {
        Context context = getContext();
        mFavoriteDbHelper = new FavoriteDbHelper(context);
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] strings, @Nullable String s,
                        @Nullable String[] strings1, @Nullable String s1) {
        final SQLiteDatabase db = mFavoriteDbHelper.getReadableDatabase();

        int match = sUriMatcher.match(uri);
        Cursor cursor;

        switch (match) {
            case FAVORITES:
                cursor = db.query(FavoriteContract.FavoriteEntry.TABLE_NAME,
                        strings,
                        s,
                        strings1,
                        null,
                        null,
                        s1);
                break;
            case FAVORITE_WITH_ID:
                String id = uri.getPathSegments().get(1);
                cursor = db.query(FavoriteContract.FavoriteEntry.TABLE_NAME,
                        strings,
                        "_id=?",
                        new String[]{id},
                        null,
                        null,
                        s1);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        cursor.setNotificationUri(getContext().getContentResolver(), uri);

        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        final SQLiteDatabase db = mFavoriteDbHelper.getWritableDatabase();

        int match = sUriMatcher.match(uri);
        Uri returnUri;

        switch (match) {
            case FAVORITES:
                long id = db.insert(FavoriteContract.FavoriteEntry.TABLE_NAME,
                        null, contentValues);
                if (id > 0) {
                    returnUri = ContentUris.withAppendedId(FavoriteContract.FavoriteEntry.CONTENT_URI, id);
                } else {
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                }
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        getContext().getContentResolver().notifyChange(uri, null);

        return returnUri;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String s, @Nullable String[] strings) {
        final SQLiteDatabase db = mFavoriteDbHelper.getWritableDatabase();
        int match = sUriMatcher.match(uri);

        int favoritesDeleted;

        switch (match) {
            case FAVORITES:
                favoritesDeleted = db.delete(FavoriteContract.FavoriteEntry.TABLE_NAME,
                        null, null);
                break;
            case FAVORITE_WITH_ID:
                String id = uri.getPathSegments().get(1);
                favoritesDeleted = db.delete(FavoriteContract.FavoriteEntry.TABLE_NAME,
                        FavoriteContract.FavoriteEntry._ID + " = ?", new String[]{id});
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        if (favoritesDeleted != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return favoritesDeleted;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s,
                      @Nullable String[] strings) {
        final SQLiteDatabase db = mFavoriteDbHelper.getWritableDatabase();
        int match = sUriMatcher.match(uri);

        int favoritesUpdated;

        switch (match) {
            case FAVORITES:
                favoritesUpdated = db.update(FavoriteContract.FavoriteEntry.TABLE_NAME,
                        contentValues, s, strings);
                break;
            case FAVORITE_WITH_ID:
                if (s == null) s = FavoriteContract.FavoriteEntry._ID + "=?";
                else s += " AND " + FavoriteContract.FavoriteEntry._ID + "=?";

                String id = uri.getPathSegments().get(1);

                if (strings == null) {
                    strings = new String[]{id};
                }
                else {
                    ArrayList<String> selectionArgsList = new ArrayList<String>();
                    selectionArgsList.addAll(Arrays.asList(strings));
                    selectionArgsList.add(id);
                    strings = selectionArgsList.toArray(new String[selectionArgsList.size()]);
                }
                favoritesUpdated = db.update(FavoriteContract.FavoriteEntry.TABLE_NAME, contentValues,
                        s, strings);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        if (favoritesUpdated != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return favoritesUpdated;
    }
}
