package com.mespana.trans2021.services;

import android.content.Context;

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
import java.util.List;

public class JsonParsingService {

    private static List<Artist> artistList;

    public static void parseJson(Context context){
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
            for (int i = 0; i < jsonarray.length(); i++) {
                artistList.add(new Artist(jsonarray.getJSONObject(i)));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static List<Artist> getArtistList(){
        return Collections.unmodifiableList(artistList);
    }
}
