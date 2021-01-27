package com.mespana.trans2021.models;

import com.firebase.ui.auth.data.model.User;

public class Note {

    private Integer stars;
    private Artist artist;
    private User user;
    private String comment;

    public Note(Integer stars, Artist artist, User user, String comment) {
        this.stars = stars;
        this.artist = artist;
        this.user = user;
    }

    public Note(String comment) {
        this.comment = comment;
    }

    public Integer getStars() {
        return stars;
    }

    public void setStars(Integer stars) {
        this.stars = stars;
    }

    public Artist getArtist() {
        return artist;
    }

    public void setArtist(Artist artist) {
        this.artist = artist;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
