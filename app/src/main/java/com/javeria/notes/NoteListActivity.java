package com.javeria.notes;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.javeria.notes.adapters.NotesRecyclerAdapter;
import com.javeria.notes.models.Note;
import com.javeria.notes.util.VerticalSpacingItemDecorator;

import java.util.ArrayList;

public class NoteListActivity extends AppCompatActivity {

    private RecyclerView         mRecyclerView;
    private NotesRecyclerAdapter mRecyclerAdapter;
    private ArrayList<Note>      mNotes = new ArrayList<>();

    private String TAG = NoteListActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = findViewById(R.id.recyclerView);
        initRecyclerView();
        insertDataInNotes();

    }


    private void insertDataInNotes() {
        for (int i = 0; i < 100; i++) {
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
        mRecyclerAdapter = new NotesRecyclerAdapter(mNotes);
        VerticalSpacingItemDecorator itemDecorator = new VerticalSpacingItemDecorator(10);
        mRecyclerView.addItemDecoration(itemDecorator);
        mRecyclerView.setAdapter(mRecyclerAdapter);
        mRecyclerAdapter.notifyDataSetChanged();
    }
}
