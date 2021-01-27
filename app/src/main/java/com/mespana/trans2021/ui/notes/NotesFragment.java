package com.mespana.trans2021.ui.notes;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
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

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
        String recordId = sharedPref.getString(getActivity().getString(R.string.shared_prefs_artist_rec_id), getActivity().getString(R.string.unknown_artists));
        Artist artist = JsonParsingService.getArtistFromRecordId(recordId);
        this.binding = FragmentNotesBinding.inflate(inflater, container, false);
        this.binding.recyclerView.setAdapter(new NotesRecyclerViewAdapter(FirebaseService.getNotesFromArtist(artist)));
        binding.confirmButton.setOnClickListener(view -> {
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            if(user != null){
                Note note = new Note((int) binding.slider.getValue(),
                        user.getProviderId(),
                        user.getDisplayName(),
                        "",// todo add user.getPhotoUrl()
                        binding.filledTextField.getEditText().getText().toString(),
                        artist.getRecordid());
                FirebaseService.postNote(note);
            }
        });
        return binding.getRoot();
    }
}
