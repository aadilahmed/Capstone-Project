package com.example.aadil.capstoneproject.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Channel implements Parcelable {
    @SerializedName("id")
    private String id;
    @SerializedName("image")
    private String image;
    @SerializedName("title")
    private String title;
    @SerializedName("publisher")
    private String publisher;

    public Channel(String id, String image, String title, String publisher) {
        this.id = id;
        this.image = image;
        this.title = title;
        this.publisher = publisher;
    }

    protected Channel(Parcel in) {
        id = in.readString();
        image = in.readString();
        title = in.readString();
        publisher = in.readString();
    }

    public static final Creator<Channel> CREATOR = new Creator<Channel>() {
        @Override
        public Channel createFromParcel(Parcel in) {
            return new Channel(in);
        }

        @Override
        public Channel[] newArray(int size) {
            return new Channel[size];
        }
    };

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImage() {
        return this.image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPublisher() {
        return this.publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(image);
        parcel.writeString(title);
        parcel.writeString(publisher);
    }
}
