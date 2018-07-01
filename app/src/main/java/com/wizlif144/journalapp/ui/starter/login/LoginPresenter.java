package com.wizlif144.journalapp.ui.starter.login;

import android.app.Activity;
import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.util.Patterns;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.FirebaseDatabase;
import com.wizlif144.journalapp.R;
import com.wizlif144.journalapp.data.prefs.PreferencesHelper;
import com.wizlif144.journalapp.ui.base.BasePresenter;
import com.wizlif144.journalapp.utils.binding.BindableString;
import com.wizlif144.journalapp.utils.rx.SchedulerProvider;

public class LoginPresenter extends BasePresenter<LoginNavigator> {

    public LoginPresenter(FirebaseDatabase firebaseDatabase, FirebaseAuth firebaseAuth, PreferencesHelper preferencesHelper, SchedulerProvider schedulerProvider) {
        super(firebaseDatabase, firebaseAuth, preferencesHelper, schedulerProvider);
    }

    public BindableString email = new BindableString();
    public BindableString password = new BindableString();

    public BindableString emailError = new BindableString();
    public BindableString passwordError = new BindableString();

    public boolean loginExecuted;

    public boolean validate(Resources res) {
        if (!loginExecuted) {
            return true;
        }
        int emailErrorRes = 0;
        int passwordErrorRes = 0;

        if (email.get().isEmpty()) {
            emailErrorRes = R.string.mandatory_field;
        } else {
            if (!Patterns.EMAIL_ADDRESS.matcher(email.get()).matches()) {
                emailErrorRes = R.string.invalid_email;
            }
        }
        if (password.get().isEmpty()) {
            passwordErrorRes = R.string.mandatory_field;
        }
        emailError.set(emailErrorRes != 0 ? res.getString(emailErrorRes) : null);
        passwordError.set(passwordErrorRes != 0 ? res.getString(passwordErrorRes) : null);

        return emailErrorRes == 0 && passwordErrorRes == 0;
    }


    public void loginEmailPassword() {
        setIsLoading(true);
        getAuthManager().signInWithEmailAndPassword(email.get(), password.get())
                .addOnSuccessListener(authResult -> {
                    setIsLoading(false);
                    FirebaseUser user = authResult.getUser();
                    if (user != null) {
                        getNavigator().onLoginSuccessful(user);
                    } else {
                        getNavigator().onError("Failed to login");
                    }
                }).
                addOnFailureListener(e -> {
                    setIsLoading(false);
                    getNavigator().onError(e.getMessage());
                });
    }


    public void loginWithGoogle(GoogleSignInAccount acct, Activity activity) {
        setIsLoading(true);
        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        getAuthManager().signInWithCredential(credential).
                addOnFailureListener(e ->
                {
                    setIsLoading(false);
                    getNavigator().onError(e.getMessage());
                }).
                addOnSuccessListener(authResult -> {
                    setIsLoading(false);
                    FirebaseUser user = getAuthManager().getCurrentUser();
                    getNavigator().onLoginSuccessful(user);
                });
    }


}
