package com.example.notespro1;

import com.google.firebase.firestore.DocumentSnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class NoteFilter {
    private List<DocumentSnapshot> originalSnapshots;
    private List<DocumentSnapshot> filteredSnapshots;

    public NoteFilter(List<DocumentSnapshot> snapshots) {
        originalSnapshots = new ArrayList<>(snapshots);
        filteredSnapshots = new ArrayList<>(snapshots);
    }

    public void filter(String query) {
        query = query.toLowerCase(Locale.getDefault());
        filteredSnapshots.clear();
        if (query.isEmpty()) {
            filteredSnapshots.addAll(originalSnapshots);
        } else {
            for (DocumentSnapshot snapshot : originalSnapshots) {
                Note note = snapshot.toObject(Note.class);
                if (note != null &&
                        (note.getTitle().toLowerCase(Locale.getDefault()).contains(query) ||
                                note.getContent().toLowerCase(Locale.getDefault()).contains(query))) {
                    filteredSnapshots.add(snapshot);
                }
            }
        }
    }

    public DocumentSnapshot getSnapshot(int position) {
        return filteredSnapshots.get(position);
    }

    public int getCount() {
        return filteredSnapshots.size();
    }
}