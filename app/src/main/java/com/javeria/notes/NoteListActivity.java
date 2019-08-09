package com.javeria.notes;

import android.arch.lifecycle.Observer;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.View;

import com.javeria.notes.adapters.NotesRecyclerAdapter;
import com.javeria.notes.db.NoteRepository;
import com.javeria.notes.models.Note;
import com.javeria.notes.util.VerticalSpacingItemDecorator;

import java.util.ArrayList;
import java.util.List;

public class NoteListActivity extends AppCompatActivity implements NotesRecyclerAdapter.OnNotesClickListener, FloatingActionButton.OnClickListener {

    private       RecyclerView         mRecyclerView;
    private       NotesRecyclerAdapter mRecyclerAdapter;
    private       FloatingActionButton floatingActionButton;
    private       ArrayList<Note>      mNotes   = new ArrayList<>();
    public static String               NOTE_KEY = "selected_note";
    private       NoteRepository       mNoteRepository;

    private String TAG = NoteListActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mNoteRepository = new NoteRepository(this);
        mRecyclerView = findViewById(R.id.recyclerView);
        floatingActionButton = findViewById(R.id.fab);
        floatingActionButton.setOnClickListener(this);
        initRecyclerView();
//        insertDataInNotes();
        retrieveNotes();
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        setTitle("Notes");
        Log.i("TEST", "onCreate");

    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i("TEST", "onStart");
        retrieveNotes();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i("TEST", "onResume");
        retrieveNotes();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i("TEST", "onPause");
        retrieveNotes();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i("TEST", "onRestart");
        retrieveNotes();

    }

    private void retrieveNotes() {
        mNoteRepository.queryNotes().observe(this, new Observer<List<Note>>() {
            @Override
            public void onChanged(@Nullable List<Note> notes) {

                if (mNotes.size() > 0) {
                    mNotes.clear();
                }
                if (notes != null) {
                    mNotes.addAll(notes);
                }
                mRecyclerAdapter.notifyDataSetChanged();
            }
        });
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
        mNoteRepository.removeNoteTask(note);
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
    public void onNoteClick(int position) {
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
