package com.mespana.trans2021.services;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashSet;
import java.util.Set;

public class SharedPreferencesService {

    private static SharedPreferences prefs;
    private final static String favartists_id = "FAVARTISTS";

    public static void load(Activity context) {
        // Prefs
        prefs = context.getPreferences(Context.MODE_PRIVATE);
        if(!prefs.contains(favartists_id)) {
            prefs.edit().putStringSet(favartists_id, new HashSet<>()).apply();
        }
    }

    public static void setFavArtists(HashSet<String> set) {
        SharedPreferences.Editor editor = prefs.edit().clear();
        editor.putStringSet(favartists_id, set).apply();
    }

    public static Set<String> getFavArtists() {
        return prefs.getStringSet(favartists_id, new HashSet<>());
    }

    public static void addArtist(String recordId) {
        Set<String> s = getFavArtists();
        s.add(recordId);
        SharedPreferences.Editor editor = prefs.edit().clear();
        editor.putStringSet(favartists_id,s).apply();
    }

    public static void removeArtist(String recordId) {
        Set<String> s = getFavArtists();
        s.remove(recordId);
        SharedPreferences.Editor editor = prefs.edit().clear();
        editor.putStringSet(favartists_id,s).apply();
    }

}
