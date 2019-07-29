package com.javeria.notes;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.javeria.notes.adapters.NotesRecyclerAdapter;
import com.javeria.notes.models.Note;
import com.javeria.notes.util.VerticalSpacingItemDecorator;

import java.util.ArrayList;

public class NoteListActivity extends AppCompatActivity implements NotesRecyclerAdapter.OnNotesClickListener {

    private RecyclerView                              mRecyclerView;
    private NotesRecyclerAdapter                      mRecyclerAdapter;
    private ArrayList<Note>                           mNotes = new ArrayList<>();
    private NotesRecyclerAdapter.OnNotesClickListener mOnNotesClickListener;

    private String TAG = NoteListActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = findViewById(R.id.recyclerView);
        initRecyclerView();
        insertDataInNotes();
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        setTitle("Notes");

    }


    private void insertDataInNotes() {
        for (int i = 1; i < 100; i++) {
            Note note = new Note();
            note.setTitle("title # " + i);
            note.setContent("content #" + i);
            note.setTimestamp("Jan 2019");
            mNotes.add(note);
        }

        mRecyclerAdapter.notifyDataSetChanged();
    }

    private void initRecyclerView() {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerAdapter = new NotesRecyclerAdapter(mNotes, this);
        VerticalSpacingItemDecorator itemDecorator = new VerticalSpacingItemDecorator(10);
        mRecyclerView.addItemDecoration(itemDecorator);
        mRecyclerView.setAdapter(mRecyclerAdapter);
        mRecyclerAdapter.notifyDataSetChanged();
    }


    @Override
    public void onNotesClick(int position) {
        Log.d(TAG, "position: ");
        Log.d(TAG, "onNotesClick: "+ position);
        Intent intent = new Intent(this, NotesActivity.class);
        intent.putExtra("selected_note", mNotes.get(position));
        startActivity(intent);

    }
}
