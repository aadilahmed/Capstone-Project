package com.example.aadil.capstoneproject.model;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Podcast implements Parcelable {
    private int id;
    private String url;
    private String episodeTitle;
    private String podcastTitle;
    private String image;

    public Podcast(JSONObject podcastObject) throws JSONException {
        this.id = podcastObject.getInt("id");
        this.url = podcastObject.getString("audio");
        this.episodeTitle = podcastObject.getString("title_original");
        this.podcastTitle = podcastObject.getString("podcast_title_original");
        this.image = podcastObject.getString("image");
    }

    public Podcast(int id, String url, String episodeTitle, String podcastTitle, String image) {
        this.id = id;
        this.url = url;
        this.episodeTitle = episodeTitle;
        this.podcastTitle = podcastTitle;
        this.image = image;
    }

    protected Podcast(Parcel in) {
        id = in.readInt();
        url = in.readString();
        episodeTitle = in.readString();
        podcastTitle = in.readString();
        image = in.readString();
    }

    public static final Creator<Podcast> CREATOR = new Creator<Podcast>() {
        @Override
        public Podcast createFromParcel(Parcel in) {
            return new Podcast(in);
        }

        @Override
        public Podcast[] newArray(int size) {
            return new Podcast[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
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

    public static ArrayList<Podcast> toPodcast(ArrayList<JSONObject> podcasts) throws JSONException {
        ArrayList<Podcast> podcastList = new ArrayList<>();

        for(int i = 0; i < podcasts.size(); i++) {
            podcastList.add(new Podcast(podcasts.get(i)));
        }

        return podcastList;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(url);
        parcel.writeString(episodeTitle);
        parcel.writeString(podcastTitle);
        parcel.writeString(image);
    }
}