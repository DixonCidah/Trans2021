package com.mespana.trans2021.ui.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.tabs.TabLayoutMediator;
import com.mespana.trans2021.R;
import com.mespana.trans2021.databinding.FragmentTabsBinding;

import androidx.annotation.NonNull;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;

public class TabsFragment extends Fragment {

    FragmentTabsBinding binding;

    @StringRes
    private static final int[] TAB_TITLES = new int[]{R.string.tab_text_1, R.string.tab_text_2, R.string.tab_text_3};

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        binding = FragmentTabsBinding.inflate(inflater, container, false);
        SectionsStateAdapter sectionsStateAdapter = new SectionsStateAdapter(this);
        binding.viewPager.setAdapter(sectionsStateAdapter);
        new TabLayoutMediator(binding.tabs, binding.viewPager,
                (tab, position) -> tab.setText(getResources().getString(TAB_TITLES[position]))
        ).attach();
        return binding.getRoot();
    }
}
