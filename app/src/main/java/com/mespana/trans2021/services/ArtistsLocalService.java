package com.mespana.trans2021.services;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import com.mespana.trans2021.R;
import com.mespana.trans2021.models.Artist;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class ArtistsLocalService {

    private static List<Artist> artistList;
    private static SharedPreferences sharedPref;
    private final static String favartists_id = "FAVARTISTS";

    public static void parseJson(Activity context){
        sharedPref = context.getPreferences(Context.MODE_PRIVATE);
        InputStream inputStream = context.getResources().openRawResource(R.raw.out);
        StringBuilder resultStringBuilder = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while ((line = br.readLine()) != null) {
                resultStringBuilder.append(line).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            JSONArray jsonarray = new JSONArray(resultStringBuilder.toString());
            artistList = new ArrayList<>();
            final Set<String> favArtists = sharedPref.getStringSet(favartists_id, new HashSet<>());
            for (int i = 0; i < jsonarray.length(); i++) {
                final Artist artist = new Artist(jsonarray.getJSONObject(i));
                artist.setFavorite(favArtists.contains(artist.getRecordid()));
                artistList.add(artist);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static Artist getArtistFromRecordId(String recordId){
        return (artistList.stream().filter(artist -> artist.getRecordid().equals(recordId)).findAny()).get();
    }

    public static List<Artist> getArtistList(){
        return Collections.unmodifiableList(artistList);
    }

    public static void addFavorite(Artist artist){
        SharedPreferences.Editor editor = sharedPref.edit();
        Set<String> favartists = sharedPref.getStringSet(favartists_id, new HashSet<>());
        favartists.add(artist.getRecordid());
        editor.putStringSet(favartists_id, favartists);
        editor.apply();
    }

    public static void removeFavorite(Artist artist) {
        SharedPreferences.Editor editor = sharedPref.edit();
        Set<String> favartists = sharedPref.getStringSet(favartists_id, new HashSet<>());
        favartists.remove(artist.getRecordid());
        editor.putStringSet(favartists_id, favartists);
        editor.apply();
    }

    public static List<Artist> getFavoriteArtists(){
        return (artistList.stream().filter(artist -> artist.isFavorite())).collect(Collectors.toList());
    }
}
