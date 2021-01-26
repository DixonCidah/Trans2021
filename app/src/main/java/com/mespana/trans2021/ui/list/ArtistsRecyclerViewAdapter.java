package com.mespana.trans2021.ui.list;

import android.app.Activity;
import android.graphics.Bitmap;
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
            SpotifyService.getPictureFromSpotifyAlbumId(artist.getSpotify(),
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

            binding.card.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // lancer le fragment de détail d'artiste
                }
            });
        }

        public ViewHolder(View view) {
            super(view);
            binding = FragmentListItemBinding.bind(view);
        }
    }

}
