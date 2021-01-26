package com.mespana.trans2021;

import android.os.Bundle;

import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.tabs.TabLayoutMediator;
import com.mespana.trans2021.databinding.ActivityMainBinding;
import com.mespana.trans2021.services.JsonParsingService;
import com.mespana.trans2021.ui.main.SectionsStateAdapter;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @StringRes
    private static final int[] TAB_TITLES = new int[]{R.string.tab_text_1, R.string.tab_text_2, R.string.tab_text_3};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        JsonParsingService.parseJson(this);
        setContentView(binding.getRoot());
        SectionsStateAdapter sectionsStateAdapter = new SectionsStateAdapter(this);
        binding.viewPager.setAdapter(sectionsStateAdapter);
        new TabLayoutMediator(binding.tabs, binding.viewPager,
                (tab, position) -> tab.setText(getResources().getString(TAB_TITLES[position]))
        ).attach();
    }
}