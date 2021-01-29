package com.mespana.trans2021.ui.search;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Toast;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import com.mespana.trans2021.R;
import com.mespana.trans2021.databinding.FragmentSearchBinding;
import com.mespana.trans2021.models.Artist;
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
        binding.countryChip.setOnCheckedChangeListener((buttonView, isChecked) -> {

        });
        binding.nameChip.setOnCheckedChangeListener((buttonView, isChecked) -> {

        });
        binding.yearChip.setOnCheckedChangeListener((buttonView, isChecked) -> {

        });
        View root = binding.getRoot();
        chooseYearOnly();
        setSearchButtonAction();
        return root;
    }

    private void setSearchButtonAction() {
        ArrayList<Artist> artistFiltres = new ArrayList<>();

        binding.searchButton.setOnClickListener(v -> {
            Log.i("MainActivity","AAAA");
            Log.i("MainActivity", "TAILLE 1 !!! (/°q°/)"+String.valueOf(artistFiltres.size()));

            if((binding.pickYear.getText() != null) && (binding.pickYear.getText().length() != 0) && !(binding.pickYear.getText().equals("@string/yearHint"))){
                // Ajouter tous les artistes avec annee == searchYear
                // Remplacer par un spinner avec les années ? (Possibilité de reprendre le code de Shervin)
                Log.i("MainActivity","123" + binding.pickYear.getText());
                artistFiltres.addAll(ArtistsLocalService.getArtistFromYear(""+binding.pickYear.getText()));
                ArtistsLocalService.setArtistListFiltre(artistFiltres);
            }
            if((binding.originSearch.getText() != null) && (binding.originSearch.getText().length() != 0) ) {
                // Ajouter tous les artistes avec originSearch == originSearch
                Log.i("MainActivity","234"+binding.originSearch.getText());
                artistFiltres.addAll(ArtistsLocalService.getArtistFromPlace(""+binding.originSearch.getText()));
                ArtistsLocalService.setArtistListFiltre(artistFiltres);
            }
            if((binding.searchName.getText() != null) && (binding.searchName.getText().length() != 0 )) {
                //Ajouter tous les artistes avec searchName == searchName
                Log.i("MainActivity","456"+binding.searchName.getText());
                artistFiltres.addAll(ArtistsLocalService.getArtistFromName(""+binding.searchName.getText()));
                ArtistsLocalService.setArtistListFiltre(artistFiltres);
            }
            Log.i("MainActivity", "TAILLE 4 !!! (/°w°/)"+String.valueOf(artistFiltres.size()));
            //Mess
            if(binding.originSearch.getText().length() == 0 && binding.originSearch.getText().length() == 0 && binding.pickYear.getText().equals("@string/yearHint")){
                artistFiltres.addAll(ArtistsLocalService.getArtistList());
                ArtistsLocalService.setArtistListFiltre(artistFiltres);
                String ff = "Vous avez oubliez de mettre des filtres";
                Toast.makeText(this.getContext(), ff ,Toast.LENGTH_SHORT).show();
            }
            else if(artistFiltres.size() == 0){
                String ff = "0 groupes ou artistes avec ces filtres";
                Toast.makeText(this.getContext(), ff ,Toast.LENGTH_SHORT).show();
            }

            // Afficher la liste des artistes trouvés via ListFragment.
            Log.i("MainActivity", "TAILLE 2 !!! (/°q°/)"+String.valueOf(artistFiltres.size()));

            for (Artist a : artistFiltres) {
                Log.i("MainActivity","ANNEE/YEAR : "+a.getAnnee()+a.getArtistes()+a.getOrigine_pays1());
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

