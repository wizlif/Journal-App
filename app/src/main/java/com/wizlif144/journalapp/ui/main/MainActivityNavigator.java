package com.wizlif144.journalapp.ui.main;

import com.wizlif144.journalapp.data.models.Note;
import com.wizlif144.journalapp.ui.main.main.notes.NoteItemPresenter;

public interface MainActivityNavigator {
    void handleError(String error);
    void handleSuccess(String message);
    void onNoteSelected(NoteItemPresenter note);
}
