package com.mespana.trans2021.models;


import com.google.firebase.firestore.DocumentSnapshot;

public class Note {

    private Integer stars;
    private String userId;
    private String recordId;

    public Note(DocumentSnapshot documentSnapshot){
        this(documentSnapshot.getLong("stars").intValue(),
                documentSnapshot.getString("userId"),
                documentSnapshot.getString("recordId"));
    }

    public Note(Integer stars, String userId ,String recordId) {
        this.stars = stars;
        this.userId = userId;
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

    public String getRecordId() {
        return recordId;
    }

    public void setRecordId(String recordId) {
        this.recordId = recordId;
    }
}
