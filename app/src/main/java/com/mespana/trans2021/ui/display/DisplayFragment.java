package com.mespana.trans2021.ui.display;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.mespana.trans2021.R;
import com.mespana.trans2021.databinding.FragmentDisplayBinding;
import com.mespana.trans2021.models.Artist;
import com.mespana.trans2021.services.JsonParsingService;

import java.util.Optional;

public class DisplayFragment extends Fragment {

    FragmentDisplayBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
        Context context = getContext();
        String recordId = sharedPref.getString(context.getString(R.string.shared_prefs_artist_rec_id), context.getString(R.string.unknown_artists));
        Artist artist = JsonParsingService.getArtistFromRecordId(recordId);
        binding = FragmentDisplayBinding.inflate(inflater, container, false);
        if(artist != null) {
            binding.artists.setText(artist.getArtistes());
            binding.country.setText(artist.getOrigine_pays1());
            binding.city.setText(artist.getOrigine_ville1());
            binding.back.setOnClickListener(view -> Navigation.findNavController(view).navigate(R.id.action_displayFragment_to_tabsFragment));
            String deezer = artist.getDeezer();
            if(deezer == null) {
                binding.deezer.setVisibility(View.GONE);
            } else {
                binding.deezer.setOnClickListener(v -> {
                    // TODO lance le profil Deezer
                });
            }

            String spotify = artist.getSpotify();
            if(spotify == null) {
                binding.spotify.setVisibility(View.GONE);
            } else {
                binding.spotify.setOnClickListener(v -> {
                    // TODO lance le profil Spotify
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse(spotify));
                    this.startActivity(intent);

                });
            }
            // si pas d'image récupérée, on met l'image de base (ic_profile)
            //binding.roundedImage.setImageDrawable();
            binding.roundedImage.setImageDrawable(getContext().getDrawable(R.drawable.samm_henshaw));
        } else {
            Toast.makeText(getContext(), "L'artiste n'existe pas", Toast.LENGTH_SHORT).show();
            Navigation.findNavController(getView()).navigate(R.id.action_displayFragment_to_tabsFragment);
        }
        View root = binding.getRoot();
        // TODO Use shared prefs to store index of clicked artist and then retrieve it inside the list
        return root;
    }
}
