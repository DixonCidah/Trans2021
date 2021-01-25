package com.mespana.trans2021.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Artist {

    private String recordid;
    private String origine_ville1;
    private String spotify;
    private long premiere_date_timestamp;
    private String cou_official_lang_code;
    private String cou_onu_code;
    private String artistes;
    private String cou_iso2_code;
    private String cou_iso3_code;
    private double geo_point_2d_x;
    private double geo_point_2d_y;
    private String premiere_salle;
    private String cou_is_receiving_quest;
    private String edition;
    private String cou_text_sp;
    private String premiere_date;
    private String cou_is_ilomember;
    private String annee;
    private String deezer;
    private String cou_text_en;
    private String origine_pays1;

    public Artist(JSONObject jsonObject) {
        try {
            this.recordid = jsonObject.has("recordid") && !jsonObject.isNull("recordid") ? jsonObject.getString("recordid") : "";
            if (jsonObject.has("fields")) {
                JSONObject fields = jsonObject.getJSONObject("fields");
                this.origine_ville1 = fields.has("origine_ville1") && !fields.isNull("origine_ville1") ? fields.getString("origine_ville1") : "";
                this.spotify = fields.has("spotify") && !fields.isNull("spotify") ? fields.getString("spotify") : "";
                this.premiere_date_timestamp = fields.has("1ere_date_timestamp") && !fields.isNull("1ere_date_timestamp") ? fields.getLong("1ere_date_timestamp") : 0;
                this.cou_official_lang_code = fields.has("cou_official_lang_code") && !fields.isNull("cou_official_lang_code") ? fields.getString("cou_official_lang_code") : "";
                this.cou_onu_code = fields.has("cou_onu_code") && !fields.isNull("cou_onu_code") ? fields.getString("cou_onu_code") : "";
                this.artistes = fields.has("artistes") && !fields.isNull("artistes") ? fields.getString("artistes") : "";
                this.cou_iso2_code = fields.has("cou_iso2_code") && !fields.isNull("cou_iso2_code") ? fields.getString("cou_iso2_code") : "";
                this.cou_iso3_code = fields.has("cou_iso3_code") && !fields.isNull("cou_iso3_code") ? fields.getString("cou_iso3_code") : "";
                this.premiere_salle = fields.has("1ere_salle") && !fields.isNull("1ere_salle") ? fields.getString("1ere_salle") : "";
                this.cou_is_receiving_quest = fields.has("cou_is_receiving_quest") && !fields.isNull("cou_is_receiving_quest") ? fields.getString("cou_is_receiving_quest") : "";
                this.edition = fields.has("edition") && !fields.isNull("edition") ? fields.getString("edition") : "";
                this.cou_text_sp = fields.has("cou_text_sp") && !fields.isNull("cou_text_sp") ? fields.getString("cou_text_sp") : "";
                this.premiere_date = fields.has("1ere_date") && !fields.isNull("1ere_date") ? fields.getString("1ere_date") : "";
                this.cou_is_ilomember = fields.has("cou_is_ilomember") && !fields.isNull("cou_is_ilomember") ? fields.getString("cou_is_ilomember") : "";
                this.annee = fields.has("annee") && !fields.isNull("annee") ? fields.getString("annee") : "";
                this.deezer = fields.has("deezer") && !fields.isNull("deezer") ? fields.getString("deezer") : "";
                this.cou_text_en = fields.has("cou_text_en") && !fields.isNull("cou_text_en") ? fields.getString("cou_text_en") : "";
                this.origine_pays1 = fields.has("origine_pays1") && !fields.isNull("origine_pays1") ? fields.getString("origine_pays1") : "";
                if (fields.has("geo_point_2d")) {
                    JSONArray geo_point_2d = fields.getJSONArray("geo_point_2d");
                    this.geo_point_2d_x = geo_point_2d.getDouble(0);
                    this.geo_point_2d_y = geo_point_2d.getDouble(1);
                }
            }
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
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

    public long getPremiere_date_timestamp() {
        return premiere_date_timestamp;
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

    public String getPremiere_salle() {
        return premiere_salle;
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

    public String getPremiere_date() {
        return premiere_date;
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
}
