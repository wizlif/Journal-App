package com.wizlif144.journalapp.ui.main;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.wizlif144.journalapp.data.prefs.PreferencesHelper;
import com.wizlif144.journalapp.ui.main.main.MonthsPagerAdapter;
import com.wizlif144.journalapp.ui.main.main.notes.NotesAdapter;
import com.wizlif144.journalapp.ui.starter.login.LoginPresenter;
import com.wizlif144.journalapp.utils.rx.SchedulerProvider;

import dagger.Module;
import dagger.Provides;

@Module
public class MainActivityModule {
    @Provides
    @Main
    MainActivityPresenter provideLoginPresenter(FirebaseDatabase firebaseDatabase, FirebaseAuth firebaseAuth, PreferencesHelper preferencesHelper, SchedulerProvider schedulerProvider) {
        return new MainActivityPresenter(firebaseDatabase, firebaseAuth, preferencesHelper, schedulerProvider);
    }

    @Provides
    MonthsPagerAdapter provideMonthsAdapter(MainActivity mainActivity) {
        return new MonthsPagerAdapter(mainActivity.getSupportFragmentManager());
    }

    @Provides
    NotesAdapter provideNotesAdapter(MainActivityPresenter mainActivityPresenter) {
        return new NotesAdapter(mainActivityPresenter);
    }
}
