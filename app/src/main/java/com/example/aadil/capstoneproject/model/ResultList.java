package com.example.aadil.capstoneproject.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ResultList {

    @SerializedName("results")
    private ArrayList<Result> resultList;

    public ArrayList<Result> getResultArrayList() {
        return resultList;
    }

    public void setResultArrayList(ArrayList<Result> resultArrayList) {
        this.resultList = resultArrayList;
    }
}
