package com.example.aadil.capstoneproject.database;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.aadil.capstoneproject.database.AppDatabase;
import com.example.aadil.capstoneproject.database.FavoriteEntry;

import java.util.List;

public class MainViewModel extends AndroidViewModel {
    private LiveData<List<FavoriteEntry>> favorites;

    public MainViewModel(@NonNull Application application) {
        super(application);
        AppDatabase db = AppDatabase.getInstance(this.getApplication());
        favorites = db.favoriteDao().loadAllFavorites();
    }

    public LiveData<List<FavoriteEntry>> getFavorites() {
        return favorites;
    }
}

