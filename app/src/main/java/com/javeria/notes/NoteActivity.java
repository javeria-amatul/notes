package com.javeria.notes;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.javeria.notes.db.NoteRepository;
import com.javeria.notes.models.Note;
import com.javeria.notes.util.Utility;

public class NoteActivity extends AppCompatActivity implements View.OnTouchListener, GestureDetector.OnGestureListener, GestureDetector.OnDoubleTapListener, View.OnClickListener, TextWatcher {

    private String      TAG = NoteActivity.class.getSimpleName();
    //Ui elements
    private ImageButton backBtn, checkBtn;
    private LineEditText   lineEditText;
    private EditText       editnoteTitle;
    private TextView       noteTitle;
    private RelativeLayout backArrowLayout, checkLayout;


    //variables
    private Note mInitialNote, mNoteFinal;
    private              boolean         mIsNewNote;
    private              GestureDetector mGestureDetector;
    private              int             mMode;
    private              int             EDIT_MODE_ENABLED  = 1;
    private              int             EDIT_MODE_DISABLED = 0;
    private static final String          MODE               = "mode";
    private              NoteRepository  mNoteRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);
        backBtn = findViewById(R.id.back_arrow_btn);
        checkBtn = findViewById(R.id.check_btn);
        editnoteTitle = findViewById(R.id.noteTitleEditText);
        noteTitle = findViewById(R.id.noteTitle);
        lineEditText = findViewById(R.id.note_text);
        backArrowLayout = findViewById(R.id.back_arrow_container);
        checkLayout = findViewById(R.id.check_container);
        mNoteRepository = new NoteRepository(getApplicationContext());
        setListeners();
        if (getIncomingNote()) {
            //Create a new note (Edit Mode)
            setNewNoteProperties();
            enableEditMode();

        } else {
            // Show selected Note(View mode)
            setNoteProperties();
            disableContentInteraction();
        }

    }

    private boolean getIncomingNote() {
        if (getIntent().hasExtra(NoteListActivity.NOTE_KEY)) {
            mMode = EDIT_MODE_DISABLED;
            mIsNewNote = false;
            mInitialNote = getIntent().getParcelableExtra(NoteListActivity.NOTE_KEY);
            mNoteFinal = new Note();
            mNoteFinal.setTitle(mInitialNote.getTitle());
            mNoteFinal.setTimestamp(mInitialNote.getTimestamp());
            mNoteFinal.setContent(mInitialNote.getContent());

            Log.d("NOTE", mInitialNote.toString());
            return mIsNewNote;
        }
        mMode = EDIT_MODE_ENABLED;
        mIsNewNote = true;
        return mIsNewNote;
    }

    private void setNewNoteProperties() {
        noteTitle.setText("Note Title");
        editnoteTitle.setText("Note title");
        mNoteFinal = new Note();
        mInitialNote = new Note();
        mNoteFinal.setTitle("Note title");
        mInitialNote.setTitle("Note title");
    }

    private void setNoteProperties() {
        noteTitle.setText(mInitialNote.getTitle());
        editnoteTitle.setText(mInitialNote.getTitle());
        lineEditText.setText(mInitialNote.getContent());
    }

    private void setListeners() {
        lineEditText.setOnTouchListener(this);
        mGestureDetector = new GestureDetector(this, this);
        checkBtn.setOnClickListener(this);
        backBtn.setOnClickListener(this);
        editnoteTitle.addTextChangedListener(this);
    }


    private void enableEditMode() {
        backArrowLayout.setVisibility(View.GONE);
        checkLayout.setVisibility(View.VISIBLE);
        noteTitle.setVisibility(View.GONE);
        editnoteTitle.setVisibility(View.VISIBLE);
        mMode = EDIT_MODE_ENABLED;
        enableContentInteraction();
    }

    private void disableEditMode() {
        backArrowLayout.setVisibility(View.VISIBLE);
        checkLayout.setVisibility(View.GONE);
        noteTitle.setVisibility(View.VISIBLE);
        editnoteTitle.setVisibility(View.GONE);

        hideSoftKeyboard();

        mMode = EDIT_MODE_DISABLED;
        disableContentInteraction();
        String timestamp = Utility.getCurrentTimestamp();

        String contentStr = lineEditText.getText().toString();
        if (contentStr.length() > 0) {
            mNoteFinal.setTitle(editnoteTitle.getText().toString());
            mNoteFinal.setContent(lineEditText.getText().toString());
            mNoteFinal.setTimestamp(timestamp);
            // If the note was altered, save it.
            if (!mNoteFinal.getContent().equals(mInitialNote.getContent())
                    || !mNoteFinal.getTitle().equals(mInitialNote.getTitle())) {
                Log.i("TEST", "disable");
                saveChanges();
            }
        }
        else
        {
            Toast.makeText(getApplicationContext(), "Enter valid input", Toast.LENGTH_LONG);
        }
    }

    private void saveChanges() {
        if (mIsNewNote) {
            saveNewNote();
        } else {
            //update note
            updateNote();
        }
    }

    private void saveNewNote() {
        mNoteRepository.insertNoteTask(mNoteFinal);
    }

    private void updateNote(){
        mNoteRepository.updateNoteTask(mNoteFinal);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.check_btn: {
                disableEditMode();
                break;
            }

            case R.id.noteTitle: {
                enableEditMode();
                editnoteTitle.requestFocus();
                editnoteTitle.setSelection(editnoteTitle.length());
                break;

            }
            case R.id.back_arrow_btn: {
                finish();
                break;
            }
        }
    }

    @Override
    public void onBackPressed() {
        if (mMode == EDIT_MODE_ENABLED) {
            onClick(checkBtn);
        } else {
            super.onBackPressed();
        }
    }

    private void disableContentInteraction() {
        lineEditText.setKeyListener(null);
        lineEditText.setFocusable(false);
        lineEditText.setFocusableInTouchMode(false);
        lineEditText.setCursorVisible(false);
        lineEditText.clearFocus();
    }

    private void enableContentInteraction() {
        lineEditText.setKeyListener(new EditText(this).getKeyListener());
        lineEditText.setFocusable(true);
        lineEditText.setFocusableInTouchMode(true);
        lineEditText.setCursorVisible(true);
        lineEditText.requestFocus();
    }

    private void hideSoftKeyboard() {
        InputMethodManager inputManager = (InputMethodManager) this.getSystemService(Activity.INPUT_METHOD_SERVICE);
        View view = this.getCurrentFocus();
        if (view == null) {
            view = new View(this);
        }
        inputManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(MODE, mMode);
    }

    @Override
    protected void onRestoreInstanceState(Bundle outState) {
        super.onRestoreInstanceState(outState);
        mMode = outState.getInt(MODE);
        if (mMode == EDIT_MODE_ENABLED) {
            enableEditMode();
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return mGestureDetector.onTouchEvent(event);
    }

    @Override
    public boolean onSingleTapConfirmed(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onDoubleTap(MotionEvent e) {
        Log.d(TAG, "onDoubleTap: Tapped");
        return false;
    }

    @Override
    public boolean onDoubleTapEvent(MotionEvent e) {
        Log.d(TAG, "onDoubleTap: Tapped");
        enableEditMode();
        return false;
    }

    @Override
    public boolean onDown(MotionEvent e) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        return false;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        noteTitle.setText(s.toString());
    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}
