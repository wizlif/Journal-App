package com.wizlif144.journalapp.ui.starter.login;

import com.google.firebase.auth.FirebaseUser;

public interface LoginNavigator {
    void onLoginSuccessful(FirebaseUser user);
    void onError(String error);
}
