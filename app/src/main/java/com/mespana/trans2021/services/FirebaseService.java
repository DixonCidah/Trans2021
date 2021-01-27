package com.mespana.trans2021.services;


import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.mespana.trans2021.models.Artist;
import com.mespana.trans2021.models.Note;

public class FirebaseService {

   public static Query getNotesFromArtist(Artist artist) {
      FirebaseFirestore firestore = FirebaseFirestore.getInstance();
      return firestore.collection("notes").whereEqualTo("recordId",artist.getRecordid());
   }

   public static void postNote(Note note){
      FirebaseFirestore firestore = FirebaseFirestore.getInstance();
      firestore.collection("notes").add(note);
   }
}
