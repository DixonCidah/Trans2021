package com.mespana.trans2021.ui.profile;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.mespana.trans2021.R;
import com.mespana.trans2021.databinding.FragmentListItemBinding;
import com.mespana.trans2021.databinding.FragmentProfileFaveArtistsBinding;
import com.mespana.trans2021.models.Artist;
import com.mespana.trans2021.services.SpotifyService;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class FavoriteArtistsRecyclerViewAdapter extends RecyclerView.Adapter<FavoriteArtistsRecyclerViewAdapter.ViewHolder> {

    private final List<Artist> artistList;

    private Activity activity;
    private View view;

    public FavoriteArtistsRecyclerViewAdapter(Activity activity, List<Artist> items) {
        this.activity = activity;
        artistList = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_profile_fave_artists, parent, false);
        return new ViewHolder(view);
    }
    
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.bind(artistList.get(position));
    }

    @Override
    public int getItemCount() {
        return artistList.size();
    }

    @Override
    public void onAttachedToRecyclerView(@NotNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        final FragmentProfileFaveArtistsBinding binding;

        public void bind(Artist artist){
            view.setOnClickListener(view -> {
                Context context = view.getContext();
                SharedPreferences sharedPref = ((Activity)context).getPreferences(Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putString(context.getString(R.string.shared_prefs_artist_rec_id), artist.getRecordid());
                editor.apply();
                Navigation.findNavController(view).navigate(R.id.action_navigation_profile_to_displayFragment);
            });
            binding.artistsImg.setImageResource(R.drawable.transmusicales_template_image);
            binding.artistsName.setText(artist.getArtistes());
            binding.country.setText(artist.getOrigine_pays1());
            if (artist.getLoadedImage() == null) {
                if(!artist.isTriedToLoadImage()) {
                    SpotifyService.getPictureFromSpotifyAlbumId(artist,
                            bitmap -> activity.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    binding.artistsImg.setImageBitmap(bitmap);
                                }
                            })
                    );
                }
            }else {
                binding.artistsImg.setImageBitmap(artist.getLoadedImage());
            }
        }

        public ViewHolder(View view) {
            super(view);
            binding = FragmentProfileFaveArtistsBinding.bind(view);
        }
    }

}
