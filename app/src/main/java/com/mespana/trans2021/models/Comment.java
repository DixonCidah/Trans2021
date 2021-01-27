package com.mespana.trans2021.models;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.Date;

public class Comment{

    private String username;
    private String userPhotoUrl;
    private String comment;
    private Date date;

    public Comment(DocumentSnapshot documentSnapshot) {
        this(documentSnapshot.getString("username"),
                documentSnapshot.getString("userPhotoUrl"),
                documentSnapshot.getString("comment"),
                documentSnapshot.getDate("date"));
    }

    public Comment(String username, String userPhotoUrl, String comment, Date date) {
        this.username = username;
        this.userPhotoUrl = userPhotoUrl;
        this.comment = comment;
        this.date = date;
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserPhotoUrl() {
        return userPhotoUrl;
    }

    public void setUserPhotoUrl(String userPhotoUrl) {
        this.userPhotoUrl = userPhotoUrl;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
