package com.javeria.notes.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.javeria.notes.R;
import com.javeria.notes.models.Note;

import java.util.ArrayList;

public class NotesRecyclerAdapter extends RecyclerView.Adapter<NotesRecyclerAdapter.ViewHolder> {
    ArrayList<Note>      mNotes = new ArrayList<>();
    OnNotesClickListener mOnNotesClickListener;

    public NotesRecyclerAdapter(ArrayList<Note> notes, OnNotesClickListener onNotesClickListener) {
        this.mNotes = notes;
        this.mOnNotesClickListener = onNotesClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_note_list_item, viewGroup, false);
        return new ViewHolder(view, mOnNotesClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.title.setText(mNotes.get(i).getTitle());
        viewHolder.timestamp.setText(mNotes.get(i).getTimestamp());
    }

    @Override
    public int getItemCount() {
        return mNotes.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView title, timestamp;
        OnNotesClickListener onNotesClickListener;

        public ViewHolder(@NonNull View itemView,
                          OnNotesClickListener notesClickListener) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            timestamp = itemView.findViewById(R.id.timestamp);
            onNotesClickListener = notesClickListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Log.d("NoteListActivity", "position: " + getAdapterPosition());
            onNotesClickListener.onNoteClick(getAdapterPosition());
        }

    }

    public interface OnNotesClickListener {
        void onNoteClick(int position);
    }


}
