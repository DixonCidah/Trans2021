package com.mespana.trans2021.models;

import com.firebase.ui.auth.data.model.User;

public class Comment {

    private Artist artist;
    private User user;
    private String comment;

    public Comment(Artist artist, User user) {
        this.artist = artist;
        this.user = user;
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
