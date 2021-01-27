package com.mespana.trans2021.services;


import com.firebase.ui.auth.data.model.User;
import com.mespana.trans2021.models.Artist;
import com.mespana.trans2021.models.Note;
import java.util.Arrays;
import java.util.List;

public class FirebaseService {


   public static List<Note> getNotesFromArtist(Artist artist) {
      return Arrays.asList(new Note("test"),new Note("test1"),new Note("test1"),
              new Note("test1"),new Note("test1"),new Note("test1"),new Note("test1"),new Note("test1"),new Note("test1"),
              new Note("test1"),new Note("test1"),new Note("test1"),new Note("test1"),new Note("test1"),new Note("test1"),
               new Note("test1"),new Note("test1"),new Note("test1"),new Note("test1"),new Note("test1"),new Note("test1"));
   }

   public static void postNoteOfArtist(Artist artist, User author, Note note){

   }
}
