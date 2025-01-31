package com.mespana.trans2021.ui.comment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.mespana.trans2021.MainActivity;
import com.mespana.trans2021.R;
import com.mespana.trans2021.databinding.FragmentCommentsBinding;
import com.mespana.trans2021.models.Artist;
import com.mespana.trans2021.models.Comment;

import com.mespana.trans2021.services.FirebaseService;
import com.mespana.trans2021.services.ArtistsLocalService;

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
        Artist artist = ArtistsLocalService.getArtistFromRecordId(recordId);
        this.binding = FragmentCommentsBinding.inflate(inflater, container, false);
        this.binding.recyclerView.setAdapter(new CommentsRecyclerViewAdapter(FirebaseService.getCommentsFromArtistRecordId(artist.getRecordid()), getActivity()));
        this.binding.filledTextField.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.toString().trim().length()==0){
                    binding.confirmButton.setEnabled(false);
                } else {
                    binding.confirmButton.setEnabled(true);
                }
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) { }

            @Override
            public void afterTextChanged(Editable s) { }
        });
        this.binding.confirmButton.setOnClickListener(view -> {
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            if(user != null){
                Comment comment = new Comment(user.getDisplayName(),
                        user.getPhotoUrl().toString(), // url photo
                        this.binding.filledTextField.getEditText().getText().toString(),
                        new Date(System.currentTimeMillis()),
                        artist.getRecordid());
                FirebaseService.postComment(comment);
                this.binding.filledTextField.getEditText().setText("");
                Context context = getContext();
                if(context != null) {
                    InputMethodManager inputManager = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    if(inputManager != null) inputManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(),InputMethodManager.HIDE_NOT_ALWAYS);
                }
                this.binding.recyclerView.smoothScrollToPosition(0);
            }else{
                ((MainActivity)getActivity()).needsToSignIn();
            }
        });
        return binding.getRoot();
    }
}
