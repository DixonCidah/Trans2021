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
import com.mespana.trans2021.services.ArtistsLocalService;
import com.whiteelephant.monthpicker.MonthPickerDialog;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class SearchFragment extends Fragment{
    FragmentSearchBinding binding;
    List<Artist> artistList;
    String msghint = "Cliquez pour selectionner l'année";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = FragmentSearchBinding.inflate(inflater,container,false);
        artistList = ArtistsLocalService.getArtistList();
        View root = binding.getRoot();
        chooseYearOnly();
        setSearchButtonAction();
        return root;
    }

    private void setSearchButtonAction() {

        binding.searchButton.setOnClickListener(v -> {

            if((binding.pickYear.getText() != null) && (binding.pickYear.getText().length() != 0) && !(binding.pickYear.getText().equals(msghint))){
                // Ajouter tous les artistes avec annee == searchYear
                // Remplacer par un spinner avec les années ? (Possibilité de reprendre le code de Shervin)
                ArtistsLocalService.getArtistFromYear(""+binding.pickYear.getText());
                //ArtistsLocalService.setArtistListFiltre(artistFiltres);
            }
            if((binding.originSearch.getText() != null) && (binding.originSearch.getText().length() != 0) ) {
                // Ajouter tous les artistes avec originSearch == originSearch
                ArtistsLocalService.getArtistFromPlace(""+binding.originSearch.getText());
                //ArtistsLocalService.setArtistListFiltre(artistFiltres);
            }
            if((binding.searchName.getText() != null) && (binding.searchName.getText().length() != 0 )) {
                //Ajouter tous les artistes avec searchName == searchName
                ArtistsLocalService.getArtistFromName(""+binding.searchName.getText());
               // ArtistsLocalService.setArtistListFiltre(artistFiltres);
            }


            if((binding.searchName.getText().length() == 0) && (binding.originSearch.getText().length() == 0 ) && (binding.pickYear.getText().equals(msghint))){
                String ff = "Vous avez oubliez de mettre des filtres";
                Toast.makeText(this.getContext(), ff ,Toast.LENGTH_SHORT).show();
            }
            else if(ArtistsLocalService.getArtistListFiltre().size() ==  0){
                String ff = "0 groupes ou artistes avec ces filtres";
                Toast.makeText(this.getContext(), ff ,Toast.LENGTH_SHORT).show();
            }

            // Afficher la liste des artistes trouvés via ListFragment.

            for (Artist a : ArtistsLocalService.getArtistListFiltre()) {
                Log.i("MainActivity","ANNEE/YEAR : "+a.getAnnee()+" Artiste : "+a.getArtistes()+" Origine : "+a.getOrigine_pays1());
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


