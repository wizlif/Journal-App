package com.wizlif144.journalapp.ui.main.new_note;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
abstract public class NewNoteFragmentProvider {
    @ContributesAndroidInjector
    abstract NewNoteFragment provideNewNoteFragmentFactory();
}
