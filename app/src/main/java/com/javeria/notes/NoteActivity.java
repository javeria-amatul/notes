package com.javeria.notes;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.javeria.notes.models.Note;

public class NoteActivity extends AppCompatActivity implements View.OnTouchListener, GestureDetector.OnGestureListener, GestureDetector.OnDoubleTapListener, View.OnClickListener {

    private String      TAG = NoteActivity.class.getSimpleName();
    //Ui elements
    private ImageButton backBtn, checkBtn;
    private LineEditText   lineEditText;
    private EditText       editnoteTitle;
    private TextView       noteTitle;
    private RelativeLayout backArrowLayout, checkLayout;


    //variables
    private              Note            mInitialNote;
    private              boolean         mIsNewNote;
    private              GestureDetector mGestureDetector;
    private              int             mMode;
    private              int             EDIT_MODE_ENABLED  = 1;
    private              int             EDIT_MODE_DISABLED = 0;
    private static final String          MODE               = "mode";

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
//        noteTitle.setOnClickListener(this);
        backBtn.setOnClickListener(this);
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
        disableContentInteraction();
        mMode = EDIT_MODE_DISABLED;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.check_btn: {
                hideSoftKeyboard();
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
        if(mMode == EDIT_MODE_ENABLED){
            enableEditMode();
        }
    }
}
