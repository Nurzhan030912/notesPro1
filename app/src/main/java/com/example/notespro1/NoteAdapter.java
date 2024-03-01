package com.example.notespro1;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentSnapshot;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
public class NoteAdapter extends FirestoreRecyclerAdapter<Note, NoteAdapter.NoteViewHolder> implements Filterable{

    Context context;
    private List<Note> notes;

    protected List<Note> filteredNotes;



    public NoteAdapter(@NonNull FirestoreRecyclerOptions<Note> options, Context context) {
        super(options);
        this.context = context;
        notes = new ArrayList<>();
        filteredNotes = new ArrayList<>();
    }
    @Override
    protected void onBindViewHolder(@NonNull NoteViewHolder holder, int position, @NonNull Note note) {
        Note filteredNote = filteredNotes.get(position);
        // Bind data to views
        holder.titleTextView.setText(filteredNote.getTitle());
        holder.contentTextView.setText(filteredNote.getContent());
        holder.timestampTextView.setText(Utility.timestampToString(filteredNote.getTimestamp()));

        // Set click listener
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, NoteDetailsActivity.class);
            intent.putExtra("title", filteredNote.getTitle());
            intent.putExtra("content", filteredNote.getContent());
            intent.putExtra("docId", getSnapshots().getSnapshot(position).getId());
            context.startActivity(intent);
        });
    }



    /*@Override
    protected void onBindViewHolder(@NonNull NoteViewHolder holder, int position, @NonNull Note note) {
        if (filteredNotes.size() > position) {
            Note filteredNote = filteredNotes.get(position);
            holder.titleTextView.setText(filteredNote.getTitle());
            holder.contentTextView.setText(filteredNote.getContent());
            holder.timestampTextView.setText(Utility.timestampToString(filteredNote.getTimestamp()));

            // Set click listener
            holder.itemView.setOnClickListener(v -> {
                Intent intent = new Intent(context, NoteDetailsActivity.class);
                intent.putExtra("title", filteredNote.getTitle());
                intent.putExtra("content", filteredNote.getContent());
                intent.putExtra("docId", getSnapshots().getSnapshot(position).getId());
                context.startActivity(intent);
            });
        }
    }*/



    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_note_item, parent, false);
        return new NoteViewHolder(view);
    }

    static class NoteViewHolder extends RecyclerView.ViewHolder {
        TextView titleTextView, contentTextView, timestampTextView;

        NoteViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.note_title_text_view);
            contentTextView = itemView.findViewById(R.id.note_content_text_view);
            timestampTextView = itemView.findViewById(R.id.note_timestamp_text_view);
        }
    }
   /* public void setNotes(List<Note> notes) {
        this.notes = notes;
        getFilter().filter(""); // Применить фильтр для обновления filteredNotes
    }*/
   public void setNotes(List<Note> notes) {
       this.notes = notes;
       // Убедимся, что filteredNotes содержит те же заметки, что и notes
       filteredNotes.clear();
       filteredNotes.addAll(notes);
       notifyDataSetChanged();
   }


    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String query = constraint.toString().toLowerCase().trim();
                List<Note> filteredList = new ArrayList<>();
                if (!query.isEmpty()) {
                    for (Note note : notes) {
                        if (note.getTitle().toLowerCase().contains(query) ||
                                note.getContent().toLowerCase().contains(query)) {
                            filteredList.add(note);
                        }
                    }
                }
                FilterResults results = new FilterResults();
                results.values = filteredList;
                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                List<Note> filteredList = (List<Note>) results.values;
                List<Note> remainingNotes = new ArrayList<>(notes);
                remainingNotes.removeAll(filteredList);

                filteredNotes.clear();
                filteredNotes.addAll(filteredList);
                filteredNotes.addAll(remainingNotes);

                notifyDataSetChanged();
            }

        };
    }


    public void filterNotes(String query) {
        getFilter().filter(query);
        notifyDataSetChanged();
    }
    @Override
    public int getItemCount() {
        return getSnapshots().size();
    }


}
