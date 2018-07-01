package com.wizlif144.journalapp.ui.main.main.flip_pager_item;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
abstract public class FlipFragmentProvider {
    @ContributesAndroidInjector
    abstract FlipFragment provideFlipFragmentFactory();
}
