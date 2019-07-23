package com.javeria.notes.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.javeria.notes.R;
import com.javeria.notes.models.Note;

import java.util.ArrayList;

public class NotesRecyclerAdapter extends RecyclerView.Adapter<NotesRecyclerAdapter.ViewHolder>{
    ArrayList<Note> mNotes = new ArrayList<>();

    public NotesRecyclerAdapter(ArrayList<Note> notes){
        this.mNotes = notes;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_note_list_item, viewGroup, false );
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.title.setText(mNotes.get(i).getTitle());
        viewHolder.timestamp.setText(mNotes.get(i).getTimestamp());

    }

    @Override
    public int getItemCount() {
        return mNotes.size()>0?mNotes.size():0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView title, timestamp;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            timestamp = itemView.findViewById(R.id.timestamp);
        }
    }
}
