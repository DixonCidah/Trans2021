package com.mespana.trans2021.services;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import com.mespana.trans2021.R;
import com.mespana.trans2021.models.Artist;
import com.mespana.trans2021.models.ArtistFilter;

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
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ArtistsLocalService {

    private static List<Artist> artistList;
    private static List<ArtistFilter> listOfFilters = new ArrayList<>();

    public static void parseJson(Activity context){
        SharedPreferencesService.load(context);
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
            final Set<String> favArtists = SharedPreferencesService.getFavArtists();
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
        SharedPreferencesService.addArtist(artist.getRecordid());
    }

    public static void removeFavorite(Artist artist) {
        SharedPreferencesService.removeArtist(artist.getRecordid());
    }

    public static List<Artist> getArtistListFiltre(){
        return artistList.stream().filter(artist -> {
            for (ArtistFilter artistFilter : listOfFilters){
                if (!artistFilter.getPredicate().test(artist)){
                    return false;
                }
            }
            return true;
        }).collect(Collectors.toList());
    }

    public static void addFilter(ArtistFilter artistFilter){
        listOfFilters.add(artistFilter);
    }

    public static List<ArtistFilter> getListOfFilters() {
        return listOfFilters;
    }

    public static void resetFiltres(){
        listOfFilters.clear();
    }

    public static void removeFilter(ArtistFilter artistFilter) {
        listOfFilters.remove(artistFilter);
    }
}

