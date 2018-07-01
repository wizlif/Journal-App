package com.wizlif144.journalapp.di.builder;

import com.wizlif144.journalapp.ui.main.Main;
import com.wizlif144.journalapp.ui.main.MainActivity;
import com.wizlif144.journalapp.ui.main.MainActivityModule;
import com.wizlif144.journalapp.ui.main.main.MainFragmentProvider;
import com.wizlif144.journalapp.ui.main.main.flip_pager_item.FlipFragmentProvider;
import com.wizlif144.journalapp.ui.main.main.notes.NotesFragmentProvider;
import com.wizlif144.journalapp.ui.main.new_note.NewNoteFragmentProvider;
import com.wizlif144.journalapp.ui.main.view_note.ViewNoteFragment;
import com.wizlif144.journalapp.ui.main.view_note.ViewNoteFragmentProvider;
import com.wizlif144.journalapp.ui.starter.SplashActivity;
import com.wizlif144.journalapp.ui.starter.login.LoginActivity;
import com.wizlif144.journalapp.ui.starter.login.LoginActivityModule;
import com.wizlif144.journalapp.ui.starter.signup.SignupActivity;
import com.wizlif144.journalapp.ui.starter.signup.SignupActivityModule;
import com.wizlif144.journalapp.ui.starter.welcome.WelcomeActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityBuilder {
    @ContributesAndroidInjector
    abstract SplashActivity bindSplashActivity();

    @ContributesAndroidInjector
    abstract WelcomeActivity bindWelcomeActivity();

    @ContributesAndroidInjector(modules = LoginActivityModule.class)
    abstract LoginActivity bindLoginActivity();

    @ContributesAndroidInjector(modules = SignupActivityModule.class)
    abstract SignupActivity bindSignupActivity();

    @ContributesAndroidInjector(modules = {
            MainActivityModule.class,
            MainFragmentProvider.class,
            FlipFragmentProvider.class,
            NewNoteFragmentProvider.class,
            NotesFragmentProvider.class,
            ViewNoteFragmentProvider.class
    })
    @Main
    abstract MainActivity bindMainActivity();
}
