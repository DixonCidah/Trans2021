package com.mespana.trans2021.ui.profile;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.mespana.trans2021.MainActivity;
import com.mespana.trans2021.R;
import com.mespana.trans2021.databinding.FragmentProfileBinding;
import com.mespana.trans2021.services.ArtistsLocalService;
import com.mespana.trans2021.services.RemotePictureService;
import com.mespana.trans2021.services.handlers.ImageHandler;

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
            binding.email.setText(email);
            binding.name.setText(name);
            binding.roundedImage.setOnClickListener(view -> {
                changePhotoUrl();
            });
            refreshProfilePicture();
            binding.list.setAdapter(new FavoriteArtistsRecyclerViewAdapter(getActivity(), ArtistsLocalService.getArtistList()/*TODO retrieve list through sharedPrefs*/));
        }
        return this.binding.getRoot();
    }

    private void refreshProfilePicture() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user != null) {
            RemotePictureService.getImageFromUrl(user.getPhotoUrl().toString(), new ImageHandler() {
                @Override
                public void onSuccess(Bitmap bitmap) {
                    getActivity().runOnUiThread(() -> binding.roundedImage.setImageBitmap(bitmap));
                }

                @Override
                public void onFailure() {
                }
            });
        }
    }

    private void changePhotoUrl() {
        new ChangePhotoUrlDialogBuilder(this.getActivity()).show(this.getActivity(),
                new ImageHandler() {
                    @Override
                    public void onSuccess(Bitmap bitmap) {
                        getActivity().runOnUiThread(() -> binding.roundedImage.setImageBitmap(bitmap));
                    }

                    @Override
                    public void onFailure() { }
                });
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}