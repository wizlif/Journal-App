package com.wizlif144.journalapp.ui.starter.signup;

import android.app.Activity;
import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.util.Patterns;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.wizlif144.journalapp.R;
import com.wizlif144.journalapp.data.prefs.PreferencesHelper;
import com.wizlif144.journalapp.ui.base.BasePresenter;
import com.wizlif144.journalapp.utils.AppLogger;
import com.wizlif144.journalapp.utils.binding.BindableBoolean;
import com.wizlif144.journalapp.utils.binding.BindableString;
import com.wizlif144.journalapp.utils.rx.SchedulerProvider;

public class SignupPresenter extends BasePresenter<SignupNavigator> {

    public SignupPresenter(FirebaseDatabase firebaseDatabase, FirebaseAuth firebaseAuth, PreferencesHelper preferencesHelper, SchedulerProvider schedulerProvider) {
        super(firebaseDatabase, firebaseAuth, preferencesHelper, schedulerProvider);
    }

    public BindableString email = new BindableString();
    public BindableString password = new BindableString();
    public BindableString confirmPassword = new BindableString();

    public BindableString emailError = new BindableString();
    public BindableString passwordError = new BindableString();
    public BindableString confirmPasswordError = new BindableString();

    public BindableBoolean existingUser = new BindableBoolean();

    public boolean loginExecuted;

    public void reset() {
        email.set(null);
        password.set(null);
        emailError.set(null);
        passwordError.set(null);
        loginExecuted = false;
    }

    public boolean validate(Resources res) {
        if (!loginExecuted) {
            return true;
        }
        int emailErrorRes = 0;
        int passwordErrorRes = 0;
        int confirmPasswordErrorRes = 0;
        if (email.get().isEmpty()) {
            emailErrorRes = R.string.mandatory_field;
        } else {
            if (!Patterns.EMAIL_ADDRESS.matcher(email.get()).matches()) {
                emailErrorRes = R.string.invalid_email;
            }
        }
        if (existingUser.get() && password.get().isEmpty()) {
            passwordErrorRes = R.string.mandatory_field;
        }else if(!password.get().equals(confirmPassword.get())){
            confirmPasswordErrorRes=R.string.passwords_not_match;
        }
        emailError.set(emailErrorRes != 0 ? res.getString(emailErrorRes) : null);
        passwordError.set(passwordErrorRes != 0 ? res.getString(passwordErrorRes) : null);
        confirmPasswordError.set(confirmPasswordErrorRes != 0 ? res.getString(confirmPasswordErrorRes) : null);
        return emailErrorRes == 0 && passwordErrorRes == 0 && confirmPasswordErrorRes == 0;
    }

    public void signup(Activity activity){
        setIsLoading(true);
        getAuthManager().createUserWithEmailAndPassword(email.get(), password.get())
                .addOnSuccessListener(authResult -> {
                    setIsLoading(false);
                    FirebaseUser user = authResult.getUser();
                    if(user != null) {
                        getNavigator().onSignupSuccessful(user);
                    }else {
                        getNavigator().onError("Failed to login");
                    }
                })
        .addOnFailureListener(e -> {
            setIsLoading(false);
            getNavigator().onError(e.getMessage());
        });
    }

}
