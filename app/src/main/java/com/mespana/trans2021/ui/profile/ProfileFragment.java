package com.mespana.trans2021.ui.profile;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.mespana.trans2021.MainActivity;
import com.mespana.trans2021.R;
import com.mespana.trans2021.databinding.FragmentProfileBinding;
import com.mespana.trans2021.models.Artist;
import com.mespana.trans2021.services.ArtistsLocalService;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ProfileFragment extends Fragment {

    FragmentProfileBinding binding;
    NavController navController;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.navController = NavHostFragment.findNavController(this);
        this.binding = FragmentProfileBinding.inflate(inflater, container, false);

        if (FirebaseAuth.getInstance().getCurrentUser() == null) {
            navController.navigate(R.id.login_fragment);
        }else {
            this.binding.buttonSignout.setOnClickListener(view -> ((MainActivity) getActivity()).signOut(() -> this.navController.navigate(R.id.login_fragment)));
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            String email = user.getEmail();
            String name = user.getDisplayName();
            Uri photoUrl = user.getPhotoUrl();
            binding.email.setText(email);
            binding.name.setText(name);
            binding.roundedImage.setOnClickListener(view -> {
                // TODO change profile image?
            });
            SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
            Set<String> favartists = sharedPref.getStringSet("FAVARTISTS", new HashSet<>());
            List<Artist> newList = new ArrayList<>();
            for(Artist a : ArtistsLocalService.getArtistList()) {
                if(favartists.contains(a.getRecordid())) newList.add(a);
            }
            binding.list.setAdapter(new FavoriteArtistsRecyclerViewAdapter(getActivity(), newList));
            //binding.roundedimage.setImageBitmap(); // TODO replace with Uri
        }
        return this.binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}