package com.mespana.trans2021.ui.search;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import com.mespana.trans2021.R;
import com.mespana.trans2021.databinding.FragmentSearchBinding;
import com.mespana.trans2021.models.Artist;
import com.mespana.trans2021.models.ArtistFilter;
import com.mespana.trans2021.services.ArtistsLocalService;
import com.whiteelephant.monthpicker.MonthPickerDialog;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class SearchFragment extends Fragment{
    FragmentSearchBinding binding;
    List<Artist> artistList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = FragmentSearchBinding.inflate(inflater,container,false);
        artistList = ArtistsLocalService.getArtistList();
        binding.countryChip.setCheckedIconVisible(false);
        binding.nameChip.setCheckedIconVisible(false);
        binding.yearChip.setCheckedIconVisible(false);
        binding.countryTextField.setEnabled(binding.countryChip.isChecked());
        binding.textField.setEnabled(binding.nameChip.isChecked());
        binding.pickYear.setClickable(binding.yearChip.isChecked());
        binding.countryChip.setOnCheckedChangeListener((buttonView, isChecked) -> {
            binding.layoutCountry.setVisibility(isChecked ? View.VISIBLE : View.GONE);
            binding.countryTextField.setEnabled(isChecked);
        });
        binding.nameChip.setOnCheckedChangeListener((buttonView, isChecked) -> {
            binding.layoutName.setVisibility(isChecked ? View.VISIBLE : View.GONE);
            binding.textField.setEnabled(isChecked);
        });
        binding.yearChip.setOnCheckedChangeListener((buttonView, isChecked) -> {
            binding.layoutYear.setVisibility(isChecked ? View.VISIBLE : View.GONE);
            binding.pickYear.setClickable(isChecked);
        });
        View root = binding.getRoot();
        chooseYearOnly();
        setSearchButtonAction();
        return root;
    }

    private void setSearchButtonAction() {
        binding.searchButton.setOnClickListener(v -> {
            ArtistsLocalService.resetFiltres();
            if((binding.pickYear.getText() != null) && (binding.pickYear.getText().length() != 0) && !(binding.pickYear.getText().equals(getString(R.string.yearHint))) && binding.yearChip.isChecked()){
                ArtistsLocalService.addFilter(new ArtistFilter(getString(R.string.year_search, binding.pickYear.getText().toString()),
                        artist -> artist.hasParticipatedThisYear(binding.pickYear.getText().toString())));
            }
            if((binding.originSearch.getText() != null) && (binding.originSearch.getText().length() != 0) && binding.countryChip.isChecked() ) {
                ArtistsLocalService.addFilter(new ArtistFilter(getString(R.string.country_search, binding.originSearch.getText().toString()),
                        artist -> artist.getOrigine_pays1().toLowerCase().contains(binding.originSearch.getText().toString().toLowerCase())));
            }
            if((binding.searchName.getText() != null) && (binding.searchName.getText().length() != 0 ) && binding.nameChip.isChecked()) {
                ArtistsLocalService.addFilter(new ArtistFilter(getString(R.string.name_search, binding.searchName.getText().toString()),
                        artist -> artist.getArtistes().toLowerCase().contains(binding.searchName.getText().toString().toLowerCase())));
            }

            if((binding.searchName.getText().length() == 0) && (binding.originSearch.getText().length() == 0 ) && (binding.pickYear.getText().equals(getString(R.string.yearHint)))){
                Toast.makeText(this.getContext(), getString(R.string.no_filter) ,Toast.LENGTH_SHORT).show();
            }

            Navigation.findNavController(v).navigate(R.id.action_navigation_search_to_navigation_list);
        });

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


