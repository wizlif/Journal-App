package com.wizlif144.journalapp.ui.main.view_note;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
abstract public class ViewNoteFragmentProvider {
    @ContributesAndroidInjector
    abstract ViewNoteFragment provideViewNoteFragmentFactory();
}
