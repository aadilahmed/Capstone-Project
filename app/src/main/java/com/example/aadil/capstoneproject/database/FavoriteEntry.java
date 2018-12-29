package com.example.aadil.capstoneproject.database;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "favorite")
public class FavoriteEntry {
    @PrimaryKey
    private int id;
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

    public FavoriteEntry(int id, String url, String episodeTitle, String podcastTitle, String image) {
        this.id = id;
        this.url = url;
        this.episodeTitle = episodeTitle;
        this.podcastTitle = podcastTitle;
        this.image = image;
    }

    public int getId(){
        return id;
    }
    public void setId(int id) {
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
