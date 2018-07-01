package com.wizlif144.journalapp.ui.starter;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.wizlif144.journalapp.R;
import com.wizlif144.journalapp.data.prefs.PreferencesHelper;
import com.wizlif144.journalapp.ui.main.MainActivity;
import com.wizlif144.journalapp.ui.starter.login.LoginActivity;
import com.wizlif144.journalapp.ui.starter.welcome.WelcomeActivity;

import javax.inject.Inject;

import dagger.android.AndroidInjection;

public class SplashActivity extends AppCompatActivity {

    @Inject
    PreferencesHelper preferencesHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        AndroidInjection.inject(this);
        if (!preferencesHelper.getIsLoggedIn()) {
            newActivity(LoginActivity.class);
        } else if (preferencesHelper.getIsFirstTimeLaunch()) {
            newActivity(WelcomeActivity.class);
        } else {
            newActivity(MainActivity.class);
        }

        finish();
    }

    public <Z> Intent newIntent(Class<Z> clazz) {
        return new Intent(this, clazz);
    }

    public <Z> void newActivity(Class<Z> clazz) {
        startActivity(newIntent(clazz));
    }

}
