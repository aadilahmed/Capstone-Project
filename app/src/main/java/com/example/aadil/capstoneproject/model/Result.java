package com.example.aadil.capstoneproject.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Result implements Parcelable {
    @SerializedName("id")
    private String id;
    @SerializedName("audio")
    private String url;
    @SerializedName("title_original")
    private String episodeTitle;
    @SerializedName("podcast_title_original")
    private String podcastTitle;
    @SerializedName("image")
    private String image;

    public Result(String id, String url, String episodeTitle, String podcastTitle, String image) {
        this.id = id;
        this.url = url;
        this.episodeTitle = episodeTitle;
        this.podcastTitle = podcastTitle;
        this.image = image;
    }

    protected Result(Parcel in) {
        id = in.readString();
        url = in.readString();
        episodeTitle = in.readString();
        podcastTitle = in.readString();
        image = in.readString();
    }

    public static final Creator<Result> CREATOR = new Creator<Result>() {
        @Override
        public Result createFromParcel(Parcel in) {
            return new Result(in);
        }

        @Override
        public Result[] newArray(int size) {
            return new Result[size];
        }
    };

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(url);
        parcel.writeString(episodeTitle);
        parcel.writeString(podcastTitle);
        parcel.writeString(image);
    }
}
