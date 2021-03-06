package com.example.aadil.capstoneproject.database;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "favorite")
public class FavoriteEntry {
    @PrimaryKey
    @NonNull
    private String id;
    private String url;
    @ColumnInfo(name = "episode_title")
    private String episodeTitle;
    @ColumnInfo(name = "podcast_title")
    private String podcastTitle;
    private String image;

    @Ignore
    public FavoriteEntry(String url, String episodeTitle, String podcastTitle, String image) {
        this.url = url;
        this.episodeTitle = episodeTitle;
        this.podcastTitle = podcastTitle;
        this.image = image;
    }

    public FavoriteEntry(String id, String url, String episodeTitle, String podcastTitle, String image) {
        this.id = id;
        this.url = url;
        this.episodeTitle = episodeTitle;
        this.podcastTitle = podcastTitle;
        this.image = image;
    }

    public String getId(){
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

    public String getUrl(){
        return url;
    }
    public void setUrl(String title) {
        this.url = url;
    }

    public String getEpisodeTitle() {
        return this.episodeTitle;
    }

    public void setEpisodeTitle(String episodeTitle) {
        this.episodeTitle = episodeTitle;
    }

    public String getPodcastTitle() {
        return this.podcastTitle;
    }

    public void setPodcastTitle(String podcastTitle) {
        this.podcastTitle = podcastTitle;
    }

    public String getImage() {
        return this.image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
