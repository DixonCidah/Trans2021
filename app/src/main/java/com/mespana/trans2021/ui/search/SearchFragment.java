package com.mespana.trans2021.ui.search;


import android.os.Bundle;
//import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.fragment.app.Fragment;

import com.mespana.trans2021.databinding.FragmentSearchBinding;
import com.whiteelephant.monthpicker.MonthPickerDialog;

import java.util.Calendar;


public class SearchFragment extends Fragment {
    FragmentSearchBinding binding;
    TextView year;
    Button year_butt;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = FragmentSearchBinding.inflate(inflater,container,false);
        View root = binding.getRoot();
        year =  binding.txtYear;
        year_butt = binding.thePicker;
        chooseYearOnly();
        return root;


    }

    private void chooseYearOnly() {
        final Calendar today = Calendar.getInstance();

        year_butt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MonthPickerDialog.Builder builder = new MonthPickerDialog.Builder(getContext(),
                        new MonthPickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(int selectedMonth, int selectedYear) {
                                year.setText(String.valueOf(selectedYear));
                            }
                        }, today.get(Calendar.YEAR), today.get(Calendar.MONTH));

                builder.setActivatedMonth(Calendar.JULY)
                        .setYearRange(1979, today.get(Calendar.YEAR))
                        .setActivatedYear(today.get(Calendar.YEAR))
                        .setTitle("(/^w^)/ ANNEE/YEAR")
                        .showYearOnly()
                        .build()
                        .show();
            }
        });
    }}

