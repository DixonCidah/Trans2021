package com.mespana.trans2021.models;


import com.google.firebase.firestore.DocumentSnapshot;

public class Note {

    private Integer stars;
    private String userId;
    private String username;
    private String userPhotoUrl;
    private String comment;
    private String recordId;

    public Note(DocumentSnapshot documentSnapshot){
        this(documentSnapshot.getLong("stars").intValue(),
                documentSnapshot.getString("userId"),
                documentSnapshot.getString("username"),
                documentSnapshot.getString("userPhotoUrl"),
                documentSnapshot.getString("comment"),
                documentSnapshot.getString("recordId"));
    }

    public Note(Integer stars, String userId, String username, String userPhotoUrl, String comment,String recordId) {
        this.stars = stars;
        this.userId = userId;
        this.username = username;
        this.userPhotoUrl = userPhotoUrl;
        this.comment = comment;
        this.recordId = recordId;
    }

    public Integer getStars() {
        return stars;
    }

    public void setStars(Integer stars) {
        this.stars = stars;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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

    public String getRecordId() {
        return recordId;
    }

    public void setRecordId(String recordId) {
        this.recordId = recordId;
    }
}
