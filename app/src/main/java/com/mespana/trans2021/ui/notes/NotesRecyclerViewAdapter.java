package com.mespana.trans2021.ui.notes;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.Query;
import com.mespana.trans2021.R;
import com.mespana.trans2021.databinding.FragmentNotesItemBinding;
import com.mespana.trans2021.models.Note;
import com.mespana.trans2021.ui.FirestoreRecyclerViewAdapter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class NotesRecyclerViewAdapter extends FirestoreRecyclerViewAdapter<Note, NotesRecyclerViewAdapter.ViewHolderNote> {

    public NotesRecyclerViewAdapter(Query query) {
        super(query);
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
        Note parsedNote = createParsedObjectFromDocumentSnapshots(position);
        holder.bind(parsedNote);
    }

    @Override
    protected Note createParsedObject(DocumentSnapshot documentSnapshot) {
        return new Note(documentSnapshot);
    }

    public class ViewHolderNote extends RecyclerView.ViewHolder {
        FragmentNotesItemBinding binding;
        public ViewHolderNote(@NonNull View view) {
            super(view);
            binding = FragmentNotesItemBinding.bind(view);
        }

        public void bind(Note note){
            binding.username.setText(note.getUsername());
            binding.comment.setText(note.getComment());
        }
    }
}