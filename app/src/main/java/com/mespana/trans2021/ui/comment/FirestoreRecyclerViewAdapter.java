package com.mespana.trans2021.ui.comment;

import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import java.util.ArrayList;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

public abstract class FirestoreRecyclerViewAdapter<DocumentSnapshotParseable, ViewHolder extends RecyclerView.ViewHolder>
        extends RecyclerView.Adapter<ViewHolder>
        implements EventListener<QuerySnapshot> {

    private ArrayList<DocumentSnapshot> documentSnapshots = new ArrayList<>();

    public FirestoreRecyclerViewAdapter(Query query) {
        if (query != null) {
            query.addSnapshotListener(this);
        }
    }

    @Override
    public int getItemCount() {
        return documentSnapshots.size();
    }

    @Override
    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
        if (error != null) {
            return;
        }
        for (DocumentChange change : value.getDocumentChanges()) {
            switch (change.getType()) {
                case ADDED:
                    documentSnapshots.add(change.getNewIndex(), change.getDocument());
                    notifyItemInserted(change.getNewIndex());
                    break;
                case MODIFIED:
                    if (change.getOldIndex() == change.getNewIndex()) {
                        documentSnapshots.set(change.getOldIndex(), change.getDocument());
                        notifyItemChanged(change.getOldIndex());
                    } else {
                        documentSnapshots.remove(change.getOldIndex());
                        documentSnapshots.add(change.getNewIndex(), change.getDocument());
                        notifyItemMoved(change.getOldIndex(), change.getNewIndex());
                    }
                    break;
                case REMOVED:
                    documentSnapshots.remove(change.getOldIndex());
                    notifyItemRemoved(change.getOldIndex());
                    break;
            }
        }
    }

    protected DocumentSnapshotParseable createParsedObjectFromDocumentSnapshots(int position) {
        return createParsedObject(documentSnapshots.get(position));
    }

    protected abstract DocumentSnapshotParseable createParsedObject(DocumentSnapshot documentSnapshot);
}