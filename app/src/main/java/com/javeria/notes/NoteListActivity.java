package com.javeria.notes;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.View;

import com.javeria.notes.adapters.NotesRecyclerAdapter;
import com.javeria.notes.models.Note;
import com.javeria.notes.util.VerticalSpacingItemDecorator;

import java.util.ArrayList;

public class NoteListActivity extends AppCompatActivity implements NotesRecyclerAdapter.OnNotesClickListener, View.OnClickListener {

    private       RecyclerView                              mRecyclerView;
    private       NotesRecyclerAdapter                      mRecyclerAdapter;
    private       FloatingActionButton                      floatingActionButton;
    private       ArrayList<Note>                           mNotes   = new ArrayList<>();
    public static String                                    NOTE_KEY = "selected_note";
    private       NotesRecyclerAdapter.OnNotesClickListener mOnNotesClickListener;

    private String TAG = NoteListActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = findViewById(R.id.recyclerView);
        floatingActionButton = findViewById(R.id.fab);
        floatingActionButton.setOnClickListener(this);
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
    private void deleteNote(Note note) {
        mNotes.remove(note);
        mRecyclerAdapter.notifyDataSetChanged();
    }
    private void initRecyclerView() {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
        new ItemTouchHelper(itemTouchHelperSimpleCallback).attachToRecyclerView(mRecyclerView);
        mRecyclerAdapter = new NotesRecyclerAdapter(mNotes, this);
        VerticalSpacingItemDecorator itemDecorator = new VerticalSpacingItemDecorator(10);
        mRecyclerView.addItemDecoration(itemDecorator);
        mRecyclerView.setAdapter(mRecyclerAdapter);
    }


    @Override
    public void onNotesClick(int position) {
        Log.d(TAG, "position: ");
        Log.d(TAG, "onNotesClick: " + position);
        Intent intent = new Intent(this, NoteActivity.class);
        intent.putExtra(NOTE_KEY, mNotes.get(position));
        startActivity(intent);

    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(this, NoteActivity.class);
        startActivity(intent);
    }
    private ItemTouchHelper.SimpleCallback itemTouchHelperSimpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder1) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
            deleteNote(mNotes.get(viewHolder.getAdapterPosition()));
        }
    };
}
