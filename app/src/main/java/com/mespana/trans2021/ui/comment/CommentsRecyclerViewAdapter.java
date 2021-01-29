package com.mespana.trans2021.ui.comment;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.Query;
import com.mespana.trans2021.R;
import com.mespana.trans2021.databinding.FragmentCommentsItemBinding;
import com.mespana.trans2021.models.Comment;
import com.mespana.trans2021.services.RemotePictureService;
import com.mespana.trans2021.services.handlers.ImageHandler;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CommentsRecyclerViewAdapter extends FirestoreRecyclerViewAdapter<Comment, CommentsRecyclerViewAdapter.ViewHolderComment> {

    private final Activity activity;

    public CommentsRecyclerViewAdapter(Query query, Activity activity) {
        super(query);
        this.activity = activity;
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
            RemotePictureService.getImageFromUrl(comment.getUserPhotoUrl(), new ImageHandler() {
                @Override
                public void onSuccess(Bitmap bitmap) {
                    activity.runOnUiThread(() -> binding.userPp.setImageBitmap(bitmap));
                }

                @Override
                public void onFailure() {
                    activity.runOnUiThread(() -> binding.userPp.setImageResource(R.mipmap.ic_launcher));
                }
            });
        }
    }
}