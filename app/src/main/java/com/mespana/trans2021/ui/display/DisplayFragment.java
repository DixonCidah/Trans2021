package com.mespana.trans2021.ui.display;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.firebase.ui.auth.AuthUI;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.mespana.trans2021.MainActivity;
import com.mespana.trans2021.R;
import com.mespana.trans2021.databinding.FragmentDisplayBinding;
import com.mespana.trans2021.models.Artist;
import com.mespana.trans2021.models.Note;
import com.mespana.trans2021.services.FirebaseService;
import com.mespana.trans2021.services.JsonParsingService;
import com.mespana.trans2021.services.SpotifyService;

import java.util.ArrayList;

public class DisplayFragment extends Fragment implements EventListener<QuerySnapshot> {

    FragmentDisplayBinding binding;
    String recordId;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
        Context context = getContext();
        recordId = sharedPref.getString(context.getString(R.string.shared_prefs_artist_rec_id), context.getString(R.string.unknown_artists));
        Artist artist = JsonParsingService.getArtistFromRecordId(recordId);
        binding = FragmentDisplayBinding.inflate(inflater, container, false);
        if(artist == null) {
            Toast.makeText(getContext(), R.string.artist_does_not_exist, Toast.LENGTH_SHORT).show();
            Navigation.findNavController(getView()).navigate(R.id.action_displayFragment_to_tabsFragment);
        }
        binding.comments.setOnClickListener(view -> Navigation.findNavController(view).navigate(R.id.action_displayFragment_to_commentsFragment));
        FirebaseService.getAverageNoteOfArtist(recordId).addSnapshotListener(this);
        binding.edition.setText(artist.getEdition());
        binding.listView.setAdapter(new PastEditionsListAdapter(artist.getEventList()));
        binding.artists.setText(artist.getArtistes());
        binding.country.setText(artist.getOrigine_pays1());
        String ville = artist.getOrigine_ville1();
        if(ville == null || ville.isEmpty()) binding.city.setVisibility(View.GONE);
        else binding.city.setText(ville);
        String deezer = artist.getDeezer();
        if(deezer.isEmpty()) {
            binding.deezer.setVisibility(View.GONE);
        } else {
            binding.deezer.setOnClickListener(v -> {
                try {
                    String url = getString(R.string.url_deezer);
                    url+=deezer;
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse(url));
                    startActivity(i);
                } catch (Exception e) {
                    Toast.makeText(getContext(), R.string.can_t_open_deezer, Toast.LENGTH_SHORT).show();
                }
            });
        }

        String spotify = artist.getSpotify();
        if(spotify.isEmpty()) {
            binding.spotify.setVisibility(View.GONE);
        } else {
            binding.spotify.setOnClickListener(v -> {
                try {
                    String url = getString(R.string.url_spotify);
                    String[] tokens = spotify.split(":");
                    url+=tokens[1]+"/"+tokens[2];
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse(url));
                    startActivity(i);
                } catch (Exception e) {
                    Toast.makeText(getContext(), R.string.can_t_open_spotify, Toast.LENGTH_SHORT).show();
                }
            });
        }
        // si pas d'image récupérée, on met l'image de base (ic_profile)
        //binding.roundedImage.setImageDrawable();
        if(artist.isTriedToLoadImage()) binding.image.setImageBitmap(artist.getLoadedImage());
        if (artist.getLoadedImage() != null){
            binding.image.setImageBitmap(artist.getLoadedImage());
        }else{
            SpotifyService.getPictureFromSpotifyAlbumId(artist,
                    bitmap -> getActivity().runOnUiThread(() -> binding.image.setImageBitmap(bitmap))
            );
        }
        binding.rate.setOnClickListener(view -> showDialog());
        View root = binding.getRoot();
        return root;
    }

    private void showDialog() {
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if (firebaseUser != null){
            Context context = getContext();
            final AlertDialog.Builder popDialog = new AlertDialog.Builder(context);
            LinearLayout linearLayout = new LinearLayout(context);
            final RatingBar rating = new RatingBar(context);

            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );

            linearLayout.setGravity(Gravity.CENTER_HORIZONTAL);

            rating.setLayoutParams(lp);
            rating.setNumStars(5);
            rating.setStepSize(1);

            //add ratingBar to linearLayout
            linearLayout.addView(rating);

            popDialog.setIcon(R.drawable.ic_star);
            popDialog.setTitle(R.string.your_rate);

            //add linearLayout to dailog
            popDialog.setView(linearLayout);

            rating.setOnRatingBarChangeListener((ratingBar, v, b) -> System.out.println("Rated val:"+v));

            // Button OK
            popDialog.setPositiveButton(android.R.string.ok,
                    (dialog, which) -> {
                        FirebaseService.postNoteOfArtist(new Note((int)rating.getRating(), firebaseUser.getUid(), recordId));
                        dialog.dismiss();
                    })
                    // Button Cancel
                    .setNegativeButton("Cancel",
                            (dialog, id) -> dialog.cancel());

            popDialog.create();
            popDialog.show();
        } else {
            ((MainActivity)this.getActivity()).needsToSignIn();
        }
    }

    private ArrayList<DocumentSnapshot> documentSnapshots = new ArrayList<>();

    @Override
    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
        // pas clean mais firestore ne permet pas de faire un Query aggrégé AVG
        for (DocumentChange change : value.getDocumentChanges()) {
            switch (change.getType()) {
                case ADDED:
                    documentSnapshots.add(change.getNewIndex(), change.getDocument());
                    break;
                case MODIFIED:
                    if (change.getOldIndex() == change.getNewIndex()) {
                        documentSnapshots.set(change.getOldIndex(), change.getDocument());
                    } else {
                        documentSnapshots.remove(change.getOldIndex());
                        documentSnapshots.add(change.getNewIndex(), change.getDocument());
                    }
                    break;
                case REMOVED:
                    documentSnapshots.remove(change.getOldIndex());
                    break;
            }
        }
        int total = 0;
        for (DocumentSnapshot documentSnapshot : documentSnapshots){
            total += documentSnapshot.getLong("stars");
        }
        binding.rating.setRating((float)total / documentSnapshots.size());
    }
}
