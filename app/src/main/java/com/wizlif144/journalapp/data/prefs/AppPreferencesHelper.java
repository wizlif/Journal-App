/*
 *  Copyright (C) 2017 MINDORKS NEXTGEN PRIVATE LIMITED
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      https://mindorks.com/license/apache-v2
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License
 */

package com.wizlif144.journalapp.data.prefs;


import android.content.Context;
import android.content.SharedPreferences;

import com.wizlif144.journalapp.di.PreferenceInfo;

import javax.inject.Inject;



public class AppPreferencesHelper  implements PreferencesHelper  {

    private static final String PREF_KEY_CURRENT_USER_EMAIL = "PREF_KEY_CURRENT_USER_EMAIL";

    private static final String PREF_KEY_CURRENT_USER_ID = "PREF_KEY_CURRENT_USER_ID";

    private static final String PREF_KEY_IS_LOGGED_IN = "PREF_KEY_IS_LOGGED_IN";

    private static final String PREF_KEY_IS_FIRST_TIME_LAUNCH = "PREF_KEY_IS_FIRST_TIME_LAUNCH";

    private final SharedPreferences mPrefs;

    @Inject
    public AppPreferencesHelper(Context context, @PreferenceInfo String prefFileName){
        mPrefs = context.getSharedPreferences(prefFileName, Context.MODE_PRIVATE);
    }


    @Override
    public String getCurrentUserEmail() {
        return mPrefs.getString(PREF_KEY_CURRENT_USER_EMAIL, null);
    }

    @Override
    public void setCurrentUserEmail(String email) {
        mPrefs.edit().putString(PREF_KEY_CURRENT_USER_EMAIL, email).apply();
    }

    @Override
    public String getCurrentUserId() {
        return mPrefs.getString(PREF_KEY_CURRENT_USER_ID, "");
    }

    @Override
    public void setCurrentUserId(String userId) {
        mPrefs.edit().putString(PREF_KEY_CURRENT_USER_ID, userId).apply();
    }

    @Override
    public Boolean getIsLoggedIn() {
        return mPrefs.getBoolean(PREF_KEY_IS_LOGGED_IN, false);
    }

    @Override
    public void setIsLoggedIn(Boolean isLoggedIn) {
        mPrefs.edit().putBoolean(PREF_KEY_IS_LOGGED_IN, isLoggedIn).apply();
    }

    @Override
    public Boolean getIsFirstTimeLaunch() {
        return mPrefs.getBoolean(PREF_KEY_IS_FIRST_TIME_LAUNCH, true);
    }

    @Override
    public void setIsFirstTimeLaunch(Boolean firstTimeLaunch) {
        mPrefs.edit().putBoolean(PREF_KEY_IS_FIRST_TIME_LAUNCH, firstTimeLaunch).apply();
    }

}
