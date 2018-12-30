package com.example.aadil.capstoneproject.model;

import com.google.gson.annotations.SerializedName;

public class Result {
    @SerializedName("id")
    private int id;
    @SerializedName("audio")
    private String url;
    @SerializedName("title_original")
    private String episodeTitle;
    @SerializedName("podcast_title_original")
    private String podcastTitle;
    @SerializedName("image")
    private String image;

    public Result(int id, String url, String episodeTitle, String podcastTitle, String image) {
        this.id = id;
        this.url = url;
        this.episodeTitle = episodeTitle;
        this.podcastTitle = podcastTitle;
        this.image = image;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
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
