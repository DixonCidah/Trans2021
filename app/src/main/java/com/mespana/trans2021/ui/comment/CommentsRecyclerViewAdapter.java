package com.mespana.trans2021.ui.comment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.Query;
import com.mespana.trans2021.R;
import com.mespana.trans2021.databinding.FragmentCommentsItemBinding;
import com.mespana.trans2021.models.Comment;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CommentsRecyclerViewAdapter extends FirestoreRecyclerViewAdapter<Comment, CommentsRecyclerViewAdapter.ViewHolderComment> {

    public CommentsRecyclerViewAdapter(Query query) {
        super(query);
    }

    @NonNull
    @Override
    public ViewHolderComment onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_comments_item, parent, false);
        return new ViewHolderComment(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderComment holder, int position) {
        Comment parsedComment = createParsedObjectFromDocumentSnapshots(position);
        holder.bind(parsedComment);
    }

    @Override
    protected Comment createParsedObject(DocumentSnapshot documentSnapshot) {
        return new Comment(documentSnapshot);
    }

    public class ViewHolderComment extends RecyclerView.ViewHolder {
        FragmentCommentsItemBinding binding;
        public ViewHolderComment(@NonNull View view) {
            super(view);
            binding = FragmentCommentsItemBinding.bind(view);
        }

        public void bind(Comment comment){
            binding.username.setText(comment.getUsername());
            binding.comment.setText(comment.getComment());
        }
    }
}