package com.wizlif144.journalapp.ui.starter.login;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.wizlif144.journalapp.data.prefs.PreferencesHelper;
import com.wizlif144.journalapp.utils.rx.SchedulerProvider;

import dagger.Module;
import dagger.Provides;

@Module
public class LoginActivityModule {

    @Provides
    LoginPresenter provideLoginPresenter(FirebaseDatabase firebaseDatabase, FirebaseAuth firebaseAuth, PreferencesHelper preferencesHelper, SchedulerProvider schedulerProvider) {
        return new LoginPresenter(firebaseDatabase, firebaseAuth, preferencesHelper, schedulerProvider);
    }
}
