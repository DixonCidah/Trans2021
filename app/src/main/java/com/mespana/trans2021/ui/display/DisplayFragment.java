package com.mespana.trans2021.ui.display;

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
import androidx.navigation.Navigation;

import com.mespana.trans2021.R;
import com.mespana.trans2021.databinding.FragmentDisplayBinding;
import com.mespana.trans2021.models.Artist;
import com.mespana.trans2021.services.JsonParsingService;
import com.mespana.trans2021.services.SpotifyService;

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
        if(artist == null) {
            Toast.makeText(getContext(), "L'artiste n'existe pas", Toast.LENGTH_SHORT).show();
            Navigation.findNavController(getView()).navigate(R.id.action_displayFragment_to_tabsFragment);
        }
        binding.textviewNotes.setOnClickListener(view -> Navigation.findNavController(view).navigate(R.id.action_displayFragment_to_notesFragment));
        binding.edition.setText(artist.getEdition());
        binding.listView.setAdapter(new PastEditionsListAdapter(artist.getEventList()));
        binding.artists.setText(artist.getArtistes());
        binding.country.setText(artist.getOrigine_pays1());
        String ville = artist.getOrigine_ville1();
        if(ville == null || ville.isEmpty()) binding.city.setVisibility(View.GONE);
        else binding.city.setText(ville);
        String deezer = artist.getDeezer();
        if(deezer.isEmpty() || deezer == null) {
            binding.deezer.setVisibility(View.GONE);
        } else {
            binding.deezer.setOnClickListener(v -> {
                try {
                    String url = "https://www.deezer.com/fr/album/";
                    url+=deezer;
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse(url));
                    startActivity(i);
                } catch (Exception e) {
                    Toast.makeText(getContext(), "Vous n'avez pas d'application qui puisse permettre d'ouvrir le profil Deezer.", Toast.LENGTH_SHORT).show();
                }
            });
        }

        String spotify = artist.getSpotify();
        if(spotify.isEmpty() || spotify == null) {
            binding.spotify.setVisibility(View.GONE);
        } else {
            binding.spotify.setOnClickListener(v -> {
                try {
                    String url = "https://open.spotify.com/";
                    String[] tokens = spotify.split(":");
                    url+=tokens[1]+"/"+tokens[2];
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse(url));
                    startActivity(i);
                } catch (Exception e) {
                    Toast.makeText(getContext(), "Vous n'avez pas d'application qui puisse permettre d'ouvrir le profil Spotify.", Toast.LENGTH_SHORT).show();
                }
            });
        }
        // si pas d'image récupérée, on met l'image de base (ic_profile)
        //binding.roundedImage.setImageDrawable();
        if(artist.isTriedToLoadImage()) binding.image.setImageBitmap(artist.getLoadedImage());
        if (artist.getLoadedImage() != null){
            binding.image.setImageBitmap(artist.getLoadedImage());
        }else{
            SpotifyService.getPictureFromSpotifyAlbumId(artist,
                    bitmap -> getActivity().runOnUiThread(() -> binding.image.setImageBitmap(bitmap))
            );
        }

        View root = binding.getRoot();
        return root;
    }
}
