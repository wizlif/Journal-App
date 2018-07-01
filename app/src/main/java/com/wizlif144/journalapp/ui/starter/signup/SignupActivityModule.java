package com.wizlif144.journalapp.ui.starter.signup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.wizlif144.journalapp.data.prefs.PreferencesHelper;
import com.wizlif144.journalapp.utils.rx.SchedulerProvider;

import dagger.Module;
import dagger.Provides;

@Module
public class SignupActivityModule {
    @Provides
    SignupPresenter provideLoginPresenter(FirebaseDatabase firebaseDatabase, FirebaseAuth firebaseAuth, PreferencesHelper preferencesHelper, SchedulerProvider schedulerProvider) {
        return new SignupPresenter(firebaseDatabase, firebaseAuth, preferencesHelper, schedulerProvider);
    }
}
