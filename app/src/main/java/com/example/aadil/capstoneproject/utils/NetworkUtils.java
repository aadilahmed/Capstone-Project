package com.example.aadil.capstoneproject.utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

public class NetworkUtils {

    public static ArrayList<JSONObject> parsePodcastJson(String json) throws JSONException {
        JSONObject podcastList = new JSONObject(json);

        JSONArray podcastArray = podcastList.getJSONArray("results");
        ArrayList<JSONObject> podcasts = new ArrayList<>();

        for(int i = 0; i < podcastList.length(); i++) {
            podcasts.add(podcastArray.getJSONObject(i));
        }

        return podcasts;
    }

    /*Cite: AND Nanodegree Course Lesson 2.9*/
    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();

            if(hasInput) {
                return scanner.next();
            }
            else {
                return null;
            }
        }
        finally {
            urlConnection.disconnect();
        }
    }
}
