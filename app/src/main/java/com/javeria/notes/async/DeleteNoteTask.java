package com.javeria.notes.async;

import android.os.AsyncTask;

import com.javeria.notes.db.NoteDao;
import com.javeria.notes.models.Note;

public class DeleteNoteTask extends AsyncTask<Note, Void, Void> {

    NoteDao mNoteDao;
    public DeleteNoteTask(NoteDao noteDao){
        mNoteDao = noteDao;
    }

    @Override
    protected Void doInBackground(Note... note) {
        mNoteDao.deleteNote(note);
        return null;
    }
}
