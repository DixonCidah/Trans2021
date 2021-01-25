package com.mespana.trans2021.ui.list;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.mespana.trans2021.databinding.FragmentListBinding;
import com.mespana.trans2021.services.JsonParsingService;

import androidx.annotation.NonNull;
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
        binding.list.setAdapter(new ArtistsRecyclerViewAdapter(JsonParsingService.getArtistList()));
        View root = binding.getRoot();
        return root;
    }
}
