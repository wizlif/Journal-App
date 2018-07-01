package com.wizlif144.journalapp.ui.main.main.notes;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
abstract public class NotesFragmentProvider {
    @ContributesAndroidInjector
    abstract NotesFragment provideNotesFragmentFactory();
}
