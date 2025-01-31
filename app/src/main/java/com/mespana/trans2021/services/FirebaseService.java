package com.mespana.trans2021.services;

import android.net.Uri;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.mespana.trans2021.models.Comment;
import com.mespana.trans2021.models.Note;

public class FirebaseService {

   public static Query getCommentsFromArtistRecordId(String recordId) {
      FirebaseFirestore firestore = FirebaseFirestore.getInstance();
      return firestore.collection("comments" + recordId).orderBy("date", Query.Direction.DESCENDING);
   }

   public static void postComment(Comment comment) {
      FirebaseFirestore firestore = FirebaseFirestore.getInstance();
      firestore.collection("comments" + comment.getRecordId()).add(comment);
   }

   public static Query getAverageNoteOfArtist(String recordId) {
      FirebaseFirestore firestore = FirebaseFirestore.getInstance();
      return firestore.collection("notes" + recordId);
   }

   public static void postNoteOfArtist(Note note) {
      FirebaseFirestore firestore = FirebaseFirestore.getInstance();
      firestore.collection("notes" + note.getRecordId()).document(note.getUserId()).set(note);
   }

   public static void modifyUserPhoto(String photoUrl){
      FirebaseAuth.getInstance().getCurrentUser().updateProfile(new UserProfileChangeRequest.Builder().setPhotoUri(Uri.parse(photoUrl)).build());
   }
}
