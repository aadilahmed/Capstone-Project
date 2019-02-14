package com.example.aadil.capstoneproject.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ChannelList {
    @SerializedName("channels")
    private ArrayList<Channel> channelList;

    public ArrayList<Channel> getChannelArrayList() {
        return channelList;
    }

    public void setChannelArrayList(ArrayList<Channel> channelArrayList) {
        this.channelList = channelArrayList;
    }
}
