package com.mespana.trans2021.models;

import android.graphics.Bitmap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Artist {

    private String recordid;
    private String origine_ville1;
    private String spotify;
    private String cou_official_lang_code;
    private String cou_onu_code;
    private String artistes;
    private String cou_iso2_code;
    private String cou_iso3_code;
    private double geo_point_2d_x;
    private double geo_point_2d_y;
    private String cou_is_receiving_quest;
    private String edition;
    private String cou_text_sp;
    private String cou_is_ilomember;
    private String annee;
    private String deezer;
    private String cou_text_en;
    private String origine_pays1;
    private Bitmap loadedImage;
    private boolean triedToLoadImage;
    private List<Event> eventList;

    public Artist(JSONObject jsonObject) {
        triedToLoadImage = false;
        eventList = new ArrayList<>();
        try {
            this.recordid = jsonObject.has("recordid") && !jsonObject.isNull("recordid") ? jsonObject.getString("recordid") : "";
            if (jsonObject.has("fields")) {
                JSONObject fields = jsonObject.getJSONObject("fields");
                this.origine_ville1 = fields.has("origine_ville1") ? fields.getString("origine_ville1") : "";
                this.spotify = fields.has("spotify") ? fields.getString("spotify") : "";
                this.deezer = fields.has("deezer") ? fields.getString("deezer") : "";
                this.cou_official_lang_code = fields.has("cou_official_lang_code") ? fields.getString("cou_official_lang_code") : "";
                this.cou_onu_code = fields.has("cou_onu_code") ? fields.getString("cou_onu_code") : "";
                this.artistes = fields.has("artistes") ? fields.getString("artistes") : "";
                this.cou_iso2_code = fields.has("cou_iso2_code") ? fields.getString("cou_iso2_code") : "";
                this.cou_iso3_code = fields.has("cou_iso3_code") ? fields.getString("cou_iso3_code") : "";
                this.cou_is_receiving_quest = fields.has("cou_is_receiving_quest") ? fields.getString("cou_is_receiving_quest") : "";
                this.edition = fields.has("edition") ? fields.getString("edition") : "";
                this.cou_text_sp = fields.has("cou_text_sp") ? fields.getString("cou_text_sp") : "";
                this.cou_is_ilomember = fields.has("cou_is_ilomember") ? fields.getString("cou_is_ilomember") : "";
                this.annee = fields.has("annee")? fields.getString("annee") : "";
                this.cou_text_en = fields.has("cou_text_en") ? fields.getString("cou_text_en") : "";
                this.origine_pays1 = fields.has("origine_pays1") ? fields.getString("origine_pays1") : "";
                if (fields.has("geo_point_2d")) {
                    JSONArray geo_point_2d = fields.getJSONArray("geo_point_2d");
                    this.geo_point_2d_x = geo_point_2d.getDouble(0);
                    this.geo_point_2d_y = geo_point_2d.getDouble(1);
                }
                int i = 1;
                String  keyDate = "1ere_date_timestamp",
                        keySalle = "1ere_salle";
                while(fields.has(keyDate) && fields.has(keySalle)){
                    this.eventList.add(new Event(i, fields.getString(keySalle), new Date(fields.getLong(keyDate))));
                    i++;
                    keyDate = i+"eme_date_timestamp";
                    keySalle = i+"eme_salle";
                }
            }
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public Artist(String recordid, String origine_ville1, String spotify, long premiere_date_timestamp, String cou_official_lang_code, String cou_onu_code, String artistes, String cou_iso2_code, String cou_iso3_code, double geo_point_2d_x, double geo_point_2d_y, String premiere_salle, String cou_is_receiving_quest, String edition, String cou_text_sp, String premiere_date, String cou_is_ilomember, String annee, String deezer, String cou_text_en, String origine_pays1) {
        this.recordid = recordid;
        this.origine_ville1 = origine_ville1;
        this.spotify = spotify;
        this.cou_official_lang_code = cou_official_lang_code;
        this.cou_onu_code = cou_onu_code;
        this.artistes = artistes;
        this.cou_iso2_code = cou_iso2_code;
        this.cou_iso3_code = cou_iso3_code;
        this.geo_point_2d_x = geo_point_2d_x;
        this.geo_point_2d_y = geo_point_2d_y;
        this.cou_is_receiving_quest = cou_is_receiving_quest;
        this.edition = edition;
        this.cou_text_sp = cou_text_sp;
        this.cou_is_ilomember = cou_is_ilomember;
        this.annee = annee;
        this.deezer = deezer;
        this.cou_text_en = cou_text_en;
        this.origine_pays1 = origine_pays1;
    }

    public String getRecordid() {
        return recordid;
    }

    public String getOrigine_ville1() {
        return origine_ville1;
    }

    public String getSpotify() {
        return spotify;
    }

    public String getCou_official_lang_code() {
        return cou_official_lang_code;
    }

    public String getCou_onu_code() {
        return cou_onu_code;
    }

    public String getArtistes() {
        return artistes;
    }

    public String getCou_iso2_code() {
        return cou_iso2_code;
    }

    public String getCou_iso3_code() {
        return cou_iso3_code;
    }

    public double getGeo_point_2d_x() {
        return geo_point_2d_x;
    }

    public double getGeo_point_2d_y() {
        return geo_point_2d_y;
    }

    public String getCou_is_receiving_quest() {
        return cou_is_receiving_quest;
    }

    public String getEdition() {
        return edition;
    }

    public String getCou_text_sp() {
        return cou_text_sp;
    }

    public String getCou_is_ilomember() {
        return cou_is_ilomember;
    }

    public String getAnnee() {
        return annee;
    }

    public String getDeezer() {
        return deezer;
    }

    public String getCou_text_en() {
        return cou_text_en;
    }

    public String getOrigine_pays1() {
        return origine_pays1;
    }

    public Bitmap getLoadedImage() {
        return loadedImage;
    }

    public void setLoadedImage(Bitmap loadedImage) {
        this.loadedImage = loadedImage;
    }

    public boolean isTriedToLoadImage() {
        return triedToLoadImage;
    }

    public void setTriedToLoadImage(boolean triedToLoadImage) {
        this.triedToLoadImage = triedToLoadImage;
    }

    public List<Event> getEventList() {
        return eventList;
    }

    public void setEventList(List<Event> eventList) {
        this.eventList = eventList;
    }

}
