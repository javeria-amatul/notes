package com.javeria.notes.db;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.javeria.notes.models.Note;

import java.util.List;

public interface NoteDao {

    @Insert
    long[] insertNotes(Note... notes);


    @Query("SELECT * FROM notes")
    LiveData<List<Note>> getNotes();

    @Query("SELECT * FROM notes where title is :title")
    LiveData<List<Note>> getNotesOfTitle(String title);

    @Delete
    int deleteNote(Note... notes);

    @Update
    int updateNotes(Note... notes);


}
