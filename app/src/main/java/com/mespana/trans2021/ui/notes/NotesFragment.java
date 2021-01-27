package com.mespana.trans2021.ui.notes;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mespana.trans2021.R;
import com.mespana.trans2021.databinding.FragmentNotesBinding;
import com.mespana.trans2021.models.Artist;
import com.mespana.trans2021.models.Note;
import com.mespana.trans2021.services.FirebaseService;
import com.mespana.trans2021.services.JsonParsingService;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

public class NotesFragment extends Fragment {

    FragmentNotesBinding binding;
    List<Note> notes;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
        String recordId = sharedPref.getString(getActivity().getString(R.string.shared_prefs_artist_rec_id), getActivity().getString(R.string.unknown_artists));
        Artist artist = JsonParsingService.getArtistFromRecordId(recordId);
        this.notes = FirebaseService.getNotesFromArtist(artist);
        this.binding = FragmentNotesBinding.inflate(inflater, container, false);
        this.binding.recyclerView.setAdapter(new NotesRecyclerViewAdapter(notes));
        /*binding.confirmButton.setOnClickListener(view -> {
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            FirebaseService.postNoteOfArtist(artist, user,
                    new Note(binding.slider.getValue(), artist, user, binding.filledTextField.getEditText().getText().toString());
        });*/
        return binding.getRoot();
    }
}
