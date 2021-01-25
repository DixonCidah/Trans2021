package com.mespana.trans2021.services;

import android.content.Context;

import com.google.gson.Gson;
import com.mespana.trans2021.R;
import com.mespana.trans2021.models.Artist;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;

public class JsonParsingService {
    public static List<Artist> parseJson(Context context){
        Gson gson = new Gson();
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
        Artist[] object = gson.fromJson(resultStringBuilder.toString(), Artist[].class);
        return Arrays.asList(object);
    }
}
