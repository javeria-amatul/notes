package com.javeria.notes.db;


import android.arch.lifecycle.LiveData;
import android.content.Context;

import com.javeria.notes.models.Note;

import java.util.List;

public class NoteRepository {

    NoteDatabase mNoteDatabase;

    public NoteRepository(Context context) {
        mNoteDatabase.getInstance(context);
    }

    public void insertNoteTask(Note note) {

    }

    public LiveData<List<Note>> queryNotes() {
        return null;
    }

    public void updateNOteTask(Note note) {

    }
    
    public void removeNoteTask(Note note) {

    }


}
