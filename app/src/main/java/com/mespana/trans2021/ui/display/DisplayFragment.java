package com.mespana.trans2021.ui.display;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import com.mespana.trans2021.R;
import com.mespana.trans2021.databinding.FragmentDisplayBinding;
import com.mespana.trans2021.models.Artist;

public class DisplayFragment extends Fragment {

    FragmentDisplayBinding binding;
    private static final String ARG_SECTION_NUMBER = "section_number";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        binding = FragmentDisplayBinding.inflate(inflater, container, false);
        Artist test = new Artist("recordid", "Londres", "spotify", 20210125, "cou_official_lang_code", "cou_onu_code", "Samm Henshaw", "cou_iso2_code", "cou_iso3_code", 0.0, 0.0, "Parc des Expositions - Hall 5", "cou_is_receiving_quest", "26èmes Rencontres Trans Musicales", "cou_text_sp", "01-déc-04", "cou_is_ilomember", "annee", "deezer", "cou_text_en", "Royaume-Uni");
        binding.country.setText(test.getOrigine_pays1());
        binding.city.setText(test.getOrigine_ville1());

        String deezer = test.getDeezer();
        if(deezer == null) {
            binding.deezer.setVisibility(View.GONE);
        } else {
            binding.deezer.setOnClickListener(v -> {
                // TODO lance le profil Deezer
            });
        }

        String spotify = test.getDeezer();
        if(spotify == null) {
            binding.deezer.setVisibility(View.GONE);
        } else {
            binding.deezer.setOnClickListener(v -> {
                // TODO lance le profil Spotify
            });
        }
        // si pas d'image récupérée, on met l'image de base (ic_profile)
        //binding.roundedImage.setImageDrawable();
        binding.roundedImage.setImageDrawable(getContext().getDrawable(R.drawable.samm_henshaw));
        View root = binding.getRoot();
        // TODO Use shared prefs to store index of clicked artist and then retrieve it inside the list
        return root;
    }
}
