package com.wizlif144.journalapp.ui.main;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.wizlif144.journalapp.BR;
import com.wizlif144.journalapp.R;
import com.wizlif144.journalapp.data.models.Note;
import com.wizlif144.journalapp.databinding.ActivityMainBinding;
import com.wizlif144.journalapp.ui.base.BaseActivity;
import com.wizlif144.journalapp.ui.main.main.MainFragment;
import com.wizlif144.journalapp.ui.main.main.notes.NoteItemPresenter;
import com.wizlif144.journalapp.ui.main.main.notes.NotesFragment;
import com.wizlif144.journalapp.ui.main.new_note.NewNoteFragment;
import com.wizlif144.journalapp.ui.main.view_note.ViewNoteFragment;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;

public class MainActivity extends BaseActivity<ActivityMainBinding, MainActivityPresenter> implements MainActivityNavigator,HasSupportFragmentInjector {

    @Inject
    DispatchingAndroidInjector<Fragment> fragmentDispatchingAndroidInjector;

    @Inject
    MainActivityPresenter mMainPresenter;

    private ActivityMainBinding mActivityMainBinding;


    @Override
    public int getBindingVariable() {
        return BR.presenter;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public MainActivityPresenter getPresenter() {
        return mMainPresenter;
    }

    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return fragmentDispatchingAndroidInjector;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityMainBinding = getViewDataBinding();
        setUp();
    }

    private void setUp(){
        newFragment(MainFragment.newInstance(),MainFragment.TAG);
    }

    @Override
    public void handleError(String error) {

    }

    @Override
    public void handleSuccess(String message) {

    }

    @Override
    public void onNoteSelected(NoteItemPresenter note) {

    }

    @Override
    public void onFragmentDetached(String tag) {
        if(tag.equals(MainFragment.TAG)){
            finish();
            return;
        }
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentByTag(tag);
        if (fragment != null) {
            fragmentManager
                    .beginTransaction()
                    .disallowAddToBackStack()
                    .setCustomAnimations(R.anim.slide_left, R.anim.slide_right)
                    .remove(fragment)
                    .commitNow();
        }
    }
}
