package com.wizlif144.journalapp.ui.main.main.notes;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.view.KeyEvent;
import android.view.View;

import com.wizlif144.journalapp.BR;
import com.wizlif144.journalapp.R;
import com.wizlif144.journalapp.data.models.Note;
import com.wizlif144.journalapp.databinding.FragmentNotesBinding;
import com.wizlif144.journalapp.ui.base.BaseFragment;
import com.wizlif144.journalapp.ui.main.MainActivityNavigator;
import com.wizlif144.journalapp.ui.main.MainActivityPresenter;
import com.wizlif144.journalapp.ui.main.view_note.ViewNoteFragment;

import javax.inject.Inject;

public class NotesFragment extends BaseFragment<FragmentNotesBinding,MainActivityPresenter> implements MainActivityNavigator {

    private FragmentNotesBinding mFragmentNotesBinding;

    @Inject
    MainActivityPresenter mMainPresenter;

    @Inject
    NotesAdapter mNotesAdapter;


    public static String TAG = NotesFragment.class.getSimpleName();

    public static NotesFragment newInstance() {
        return new NotesFragment();
    }

    @Override
    public int getBindingVariable() {return BR.presenter;}

    @Override
    public int getLayoutId() {
        return R.layout.fragment_notes;
    }

    @Override
    public MainActivityPresenter getPresenter() {
        return mMainPresenter;
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mFragmentNotesBinding = getViewDataBinding();
        setUp();
    }

    private void setUp() {
        mMainPresenter.setNavigator(this);

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getBaseActivity());
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        mFragmentNotesBinding.rvNotes.setLayoutManager(mLayoutManager);
        mFragmentNotesBinding.rvNotes.setItemAnimator(new DefaultItemAnimator());
        mFragmentNotesBinding.rvNotes.setAdapter(mNotesAdapter);

        mMainPresenter.fetchNotes();

        mFragmentNotesBinding.back.setOnClickListener(v -> getBaseActivity().onFragmentDetached(TAG));
    }

    @Override
    public void handleError(String error) {

    }

    @Override
    public void handleSuccess(String message) {

    }

    @Override
    public void onNoteSelected(NoteItemPresenter note) {
        newFragment(ViewNoteFragment.newInstance(),ViewNoteFragment.TAG);
    }

    @Override
    public void onResume() {
        super.onResume();

        if (getView() == null) {
            return;
        }

        getView().setFocusableInTouchMode(true);
        getView().requestFocus();
        getView().setOnKeyListener((v, keyCode, event) -> {
            if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK) {
                getBaseActivity().onFragmentDetached(TAG);
                return  true;
            }
            return false;
        });

    }
}
