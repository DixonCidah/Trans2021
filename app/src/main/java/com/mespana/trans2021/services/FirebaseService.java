package com.mespana.trans2021.services;


import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.mespana.trans2021.models.Artist;
import com.mespana.trans2021.models.Comment;

public class FirebaseService {

   public static Query getCommentsFromArtist(Artist artist) {
      FirebaseFirestore firestore = FirebaseFirestore.getInstance();
      return firestore.collection("comments"+artist.getRecordid()).orderBy("date", Query.Direction.DESCENDING);
   }

   public static void postComment(Comment comment){
      FirebaseFirestore firestore = FirebaseFirestore.getInstance();
      firestore.collection("comments"+comment.getRecordId()).add(comment);
   }
}
