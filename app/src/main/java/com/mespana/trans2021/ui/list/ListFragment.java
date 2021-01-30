package com.mespana.trans2021.ui.list;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.chip.Chip;
import com.mespana.trans2021.R;
import com.mespana.trans2021.databinding.FragmentListBinding;
import com.mespana.trans2021.models.ArtistFilter;
import com.mespana.trans2021.services.ArtistsLocalService;

import androidx.annotation.NonNull;
import androidx.appcompat.view.ContextThemeWrapper;
import androidx.fragment.app.Fragment;

public class ListFragment extends Fragment {

    FragmentListBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        binding = FragmentListBinding.inflate(inflater, container, false);
        ArtistsRecyclerViewAdapter artistsRecyclerViewAdapter = new ArtistsRecyclerViewAdapter(getActivity());
        binding.list.setAdapter(artistsRecyclerViewAdapter);
        for(ArtistFilter artistFilter : ArtistsLocalService.getListOfFilters()){
            Chip chip = (Chip)getLayoutInflater().inflate(R.layout.removable_chip, binding.chipGroup, false);
            chip.setText(artistFilter.getName());
            chip.setOnClickListener(view -> {
                ArtistsLocalService.removeFilter(artistFilter);
                artistsRecyclerViewAdapter.notifyDataSetChanged();
                binding.chipGroup.removeView(chip);
            });
            binding.chipGroup.addView(chip);
        }

        View root = binding.getRoot();
        return root;
    }
}
