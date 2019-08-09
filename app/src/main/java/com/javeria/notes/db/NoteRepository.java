package com.javeria.notes.db;


import android.arch.lifecycle.LiveData;
import android.content.Context;

import com.javeria.notes.async.DeleteNoteTask;
import com.javeria.notes.async.InsertNoteTask;
import com.javeria.notes.async.UpdateNoteTask;
import com.javeria.notes.models.Note;

import java.util.List;

public class NoteRepository {

    NoteDatabase mNoteDatabase;

    public NoteRepository(Context context) {
        mNoteDatabase = NoteDatabase.getInstance(context);

    }

    public void insertNoteTask(Note note) {
            new InsertNoteTask(mNoteDatabase.getNoteDao()).execute(note);
    }

    public LiveData<List<Note>> queryNotes() {
        return mNoteDatabase.getNoteDao().getNotes();
    }

    public void updateNoteTask(Note note) {
            new UpdateNoteTask(mNoteDatabase.getNoteDao()).execute(note);
    }

    public void removeNoteTask(Note note) {
        new DeleteNoteTask(mNoteDatabase.getNoteDao()).execute(note);

    }


}
