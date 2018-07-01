package com.wizlif144.journalapp.ui.starter.signup;

import com.google.firebase.auth.FirebaseUser;

public interface SignupNavigator {
    void onSignupSuccessful(FirebaseUser user);
    void onError(String error);
}
