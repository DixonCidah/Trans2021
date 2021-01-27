package com.mespana.trans2021.ui.comment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.mespana.trans2021.R;
import com.mespana.trans2021.databinding.FragmentCommentsBinding;
import com.mespana.trans2021.models.Artist;
import com.mespana.trans2021.models.Comment;

import com.mespana.trans2021.services.FirebaseService;
import com.mespana.trans2021.services.JsonParsingService;

import java.util.Date;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

public class CommentsFragment extends Fragment {

    FragmentCommentsBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
        String recordId = sharedPref.getString(getActivity().getString(R.string.shared_prefs_artist_rec_id), getActivity().getString(R.string.unknown_artists));
        Artist artist = JsonParsingService.getArtistFromRecordId(recordId);
        this.binding = FragmentCommentsBinding.inflate(inflater, container, false);
        this.binding.recyclerView.setAdapter(new CommentsRecyclerViewAdapter(FirebaseService.getCommentsFromArtist(artist)));
        binding.confirmButton.setOnClickListener(view -> {
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            if(user != null){
                Comment comment = new Comment(user.getDisplayName(),
                        "", // url photo
                        this.binding.filledTextField.getEditText().getText().toString(),
                        new Date(System.currentTimeMillis()),
                        artist.getRecordid());
                FirebaseService.postComment(comment);
            }
        });
        return binding.getRoot();
    }
}
