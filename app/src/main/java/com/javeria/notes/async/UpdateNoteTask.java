package com.javeria.notes.async;

import android.os.AsyncTask;

import com.javeria.notes.db.NoteDao;
import com.javeria.notes.models.Note;

public class UpdateNoteTask extends AsyncTask<Note, Void, Void> {

    NoteDao mNoteDao;
    public UpdateNoteTask(NoteDao noteDao){
        mNoteDao = noteDao;
    }
    @Override
    protected Void doInBackground(Note... note) {
        mNoteDao.updateNotes(note);
        return null;
    }
}
