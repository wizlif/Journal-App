

package com.wizlif144.journalapp;

import android.app.Activity;
import android.app.Application;


import com.google.firebase.database.FirebaseDatabase;
import com.wizlif144.journalapp.di.component.DaggerAppComponent;
import com.wizlif144.journalapp.utils.AppLogger;

import javax.inject.Inject;

import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

public class JournalApp extends Application implements HasActivityInjector {

    @Inject
    DispatchingAndroidInjector<Activity> activityDispatchingAndroidInjector;

    @Inject
    CalligraphyConfig mCalligraphyConfig;

    @Inject
    FirebaseDatabase firebaseDatabase;

    @Override
    public DispatchingAndroidInjector<Activity> activityInjector() {
        return activityDispatchingAndroidInjector;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        DaggerAppComponent.builder()
                .application(this)
                .build()
                .inject(this);

        AppLogger.init();

        firebaseDatabase.setPersistenceEnabled(true);
//        firebaseDatabase.goOffline();


        CalligraphyConfig.initDefault(mCalligraphyConfig);
    }
}
