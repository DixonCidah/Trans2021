package com.mespana.trans2021.ui.list;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mespana.trans2021.R;
import com.mespana.trans2021.databinding.FragmentListItemBinding;
import com.mespana.trans2021.models.Artist;
import com.mespana.trans2021.services.SpotifyService;
import com.mespana.trans2021.services.handlers.ImageHandler;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

public class ArtistsRecyclerViewAdapter  extends RecyclerView.Adapter<ArtistsRecyclerViewAdapter.ViewHolder> {

    private final List<Artist> artistList;

    private Activity activity;

    public ArtistsRecyclerViewAdapter(Activity activity, List<Artist> items) {
        this.activity = activity;
        artistList = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_list_item, parent, false);
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
        final FragmentListItemBinding binding;

        public void bind(Artist artist){
            binding.card.setOnClickListener(view -> {
                Context context = view.getContext();
                SharedPreferences sharedPref = ((Activity)context).getPreferences(Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putString(context.getString(R.string.shared_prefs_artist_rec_id), artist.getRecordid());
                editor.apply();
                Navigation.findNavController(view).navigate(R.id.action_tabsFragment_to_displayFragment);
            });
            binding.cover.setImageResource(R.drawable.transmusicales_template_image);
            binding.title.setText(artist.getArtistes());
            if(artist.getOrigine_pays1().equals("")){
                binding.secondaryText.setVisibility(View.GONE);
            }else{
                binding.secondaryText.setVisibility(View.VISIBLE);
                binding.secondaryText.setText(artist.getOrigine_pays1());
            }
            if(artist.getOrigine_ville1().equals("")){
                binding.supportingText.setVisibility(View.GONE);
            }
            else {
                binding.supportingText.setVisibility(View.VISIBLE);
                binding.supportingText.setText(artist.getOrigine_ville1());
            }
            if (artist.getLoadedImage() == null) {
                if(!artist.isTriedToLoadImage()) {
                    SpotifyService.getPictureFromSpotifyAlbumId(artist,
                            new ImageHandler() {
                                @Override
                                public void onSuccess(Bitmap bitmap) {
                                    activity.runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            binding.cover.setImageBitmap(bitmap);
                                        }
                                    });
                                }
                            }
                    );
                }
            }else {
                binding.cover.setImageBitmap(artist.getLoadedImage());
            }
        }

        public ViewHolder(View view) {
            super(view);
            binding = FragmentListItemBinding.bind(view);
        }
    }

}
