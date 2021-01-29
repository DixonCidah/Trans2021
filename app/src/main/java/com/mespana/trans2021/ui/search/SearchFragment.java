package com.mespana.trans2021.ui.search;


import android.os.Bundle;
//import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.mespana.trans2021.databinding.FragmentSearchBinding;
import com.whiteelephant.monthpicker.MonthPickerDialog;

import java.util.ArrayList;
import java.util.Calendar;


public class SearchFragment extends Fragment {
    FragmentSearchBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = FragmentSearchBinding.inflate(inflater,container,false);
        View root = binding.getRoot();
        chooseYearOnly();
        return root;
    }

    private void setSearchButtonAction() {
        ArrayList<String> artistIds = new ArrayList<>();
        binding.searchButton.setOnClickListener(v -> {
            if(binding.pickYear.getText() != null){
                // TODO : Ajouter tous les recordId d'artistes avec annee == searchYear
                // TODO : Remplacer par un spinner avec les années ? (Possibilité de reprendre le code de Shervin)
            }
            if(binding.originSearch.getText() != null) {
                // TODO : Ajouter tous les recordId d'artistes avec annee == searchYear
            }
            if(binding.searchName.getText() != null) {
                // TODO : Ajouter tous les recordId d'artistes avec annee == searchYear
            }
        });
        // TODO : Afficher la liste des artistes trouvés via ListFragment.
    }

    private void chooseYearOnly() {
        final Calendar today = Calendar.getInstance();

        binding.pickYear.setOnClickListener(v -> {
            MonthPickerDialog.Builder builder = new MonthPickerDialog.Builder(getContext(),
                    (selectedMonth, selectedYear) -> binding.pickYear.setText(String.valueOf(selectedYear)),
                    today.get(Calendar.YEAR),
                    today.get(Calendar.MONTH));

            builder.setActivatedMonth(Calendar.JULY)
                    .setYearRange(1979, today.get(Calendar.YEAR))
                    .setActivatedYear(today.get(Calendar.YEAR))
                    .setTitle("Selection de l'année")
                    .showYearOnly()
                    .build()
                    .show();
        });
    }
}

