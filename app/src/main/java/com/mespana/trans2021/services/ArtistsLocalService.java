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
import java.util.stream.Stream;

public class ArtistsLocalService {

    private static List<Artist> artistList;
    private static List<Artist> artistListFiltre = new ArrayList<>();

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
                artistListFiltre = artistList;
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

    public static List<Artist> getFavoriteArtists(){
        return (artistList.stream().filter(artist -> artist.isFavorite())).collect(Collectors.toList());
    }

    public static List<Artist> getArtistListFiltre(){
        return Collections.unmodifiableList(artistListFiltre);
    }
    public static void setArtistListFiltre(List<Artist> listeArtistesFiltre){
        artistListFiltre = listeArtistesFiltre;
    }
    public static List<Artist> getArtistFromYear(String year){
        Stream<Artist> art = artistListFiltre.stream();
        artistListFiltre = Collections.unmodifiableList( (List<Artist>) (art.filter(artist -> artist.getAnnee().equals(year)).distinct().collect(Collectors.toList())));
        return artistListFiltre;
    }

    public static List<Artist> getArtistFromName(String name){
        Stream<Artist> art = artistListFiltre.stream();
        artistListFiltre = Collections.unmodifiableList( (List<Artist>) (art.filter(artist -> artist.getArtistes().equals(name)).distinct().collect(Collectors.toList())));
        return artistListFiltre;
    }

    public static List<Artist> getArtistFromPlace(String place){
        Stream<Artist> art = artistListFiltre.stream();
        artistListFiltre = Collections.unmodifiableList( (List<Artist>) (art.filter(artist -> artist.getOrigine_pays1().equals(place)).distinct().collect(Collectors.toList())));
        return artistListFiltre;
    }
    public static void resetFiltres(){
        artistListFiltre = artistList;
    }
}

