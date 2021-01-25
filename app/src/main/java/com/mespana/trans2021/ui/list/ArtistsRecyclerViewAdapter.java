package com.mespana.trans2021.ui.list;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mespana.trans2021.R;
import com.mespana.trans2021.databinding.FragmentListItemBinding;
import com.mespana.trans2021.models.Artist;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

public class ArtistsRecyclerViewAdapter  extends RecyclerView.Adapter<ArtistsRecyclerViewAdapter.ViewHolder> {

    private final List<Artist> artistList;
    public ArtistsRecyclerViewAdapter(List<Artist> items) {
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
        final Artist artist = artistList.get(position);
        holder.binding.textViewName.setText(artist.getArtistes());
        holder.binding.background.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // lancer le fragment de détail d'artiste
            }
        });
    }

    @Override
    public int getItemCount() {
        return artistList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        final FragmentListItemBinding binding;
        public ViewHolder(View view) {
            super(view);
            binding = FragmentListItemBinding.bind(view);
        }
    }

}
