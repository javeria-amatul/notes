package com.javeria.notes;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.javeria.notes.models.Note;

public class NotesActivity extends AppCompatActivity {

    private String TAG  = NotesActivity.class.getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);
        if(getIntent().hasExtra("selected_note"));{
            Note note = getIntent().getParcelableExtra("selected_note");
            Log.d(TAG, "note: "+note.toString());
        }

    }
}
