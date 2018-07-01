package com.wizlif144.journalapp.ui.main.main;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
abstract public class MainFragmentProvider {
    @ContributesAndroidInjector
    abstract MainFragment provideMainMenuFragmentFactory();
}
