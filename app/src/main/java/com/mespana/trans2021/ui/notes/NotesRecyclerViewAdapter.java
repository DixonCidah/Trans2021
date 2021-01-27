package com.mespana.trans2021.ui.notes;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mespana.trans2021.R;
import com.mespana.trans2021.databinding.FragmentNotesItemBinding;
import com.mespana.trans2021.models.Note;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class NotesRecyclerViewAdapter extends RecyclerView.Adapter<NotesRecyclerViewAdapter.ViewHolderNote> {
    private List<Note> noteList;

    public NotesRecyclerViewAdapter(List<Note> noteList) {
        this.noteList = noteList;
    }

    @NonNull
    @Override
    public ViewHolderNote onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_notes_item, parent, false);
        return new ViewHolderNote(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderNote holder, int position) {
        holder.bind(noteList.get(position));
    }

    @Override
    public int getItemCount() {
        return noteList.size();
    }

    public class ViewHolderNote extends RecyclerView.ViewHolder {
        FragmentNotesItemBinding binding;
        public ViewHolderNote(@NonNull View view) {
            super(view);
            binding = FragmentNotesItemBinding.bind(view);
        }

        public void bind(Note note){
            if(note.getUser() != null){
                //binding.username.setText(note.getUser().getName());
            }
            binding.comment.setText(note.getComment());
        }
    }
}