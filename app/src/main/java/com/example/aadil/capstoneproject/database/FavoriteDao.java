package com.example.aadil.capstoneproject.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface FavoriteDao {
    @Query("SELECT * FROM favorite")
    LiveData<List<FavoriteEntry>> loadAllFavorites();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertFavorite(FavoriteEntry favoriteEntry);

    @Delete
    void deleteFavorite(FavoriteEntry favoriteEntry);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateFavorite(FavoriteEntry favoriteEntry);

    @Query("SELECT * FROM favorite WHERE id = :id")
    FavoriteEntry loadFavoriteById(String id);
}
