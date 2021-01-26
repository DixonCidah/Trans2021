package com.mespana.trans2021.ui.main;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.mespana.trans2021.ui.list.ListFragment;
import com.mespana.trans2021.ui.map.MapFragment;
import com.mespana.trans2021.ui.search.SearchFragment;

public class SectionsStateAdapter extends FragmentStateAdapter {

    public SectionsStateAdapter(FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        Fragment tabDisplayed;
        switch (position) {
            case 0:
                tabDisplayed = new ListFragment();
                break;
            case 1:
                tabDisplayed = new SearchFragment();
                break;
            case 2:
                tabDisplayed = new MapFragment();
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + position);
        }
        return tabDisplayed;
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}