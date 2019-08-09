package com.javeria.notes.async;

import android.os.AsyncTask;
import android.util.Log;

import com.javeria.notes.db.NoteDao;
import com.javeria.notes.models.Note;

public class InsertNoteTask extends AsyncTask<Note, Void, Void> {

    NoteDao mNoteDao;

    public InsertNoteTask(NoteDao noteDao) {
        mNoteDao = noteDao;
    }

    @Override
    protected Void doInBackground(Note... note) {
        mNoteDao.insertNotes(note);
        Log.i("DB", "inserted note"+note.toString());
        return null;
    }
}
