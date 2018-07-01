package com.wizlif144.journalapp.ui.starter.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.Editable;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.wizlif144.journalapp.BR;
import com.wizlif144.journalapp.R;
import com.wizlif144.journalapp.databinding.ActivityLoginBinding;
import com.wizlif144.journalapp.ui.base.BaseActivity;
import com.wizlif144.journalapp.ui.main.MainActivity;
import com.wizlif144.journalapp.ui.starter.signup.SignupActivity;
import com.wizlif144.journalapp.ui.starter.welcome.WelcomeActivity;
import com.wizlif144.journalapp.utils.AppLogger;
import com.wizlif144.journalapp.utils.binding.TextWatcherAdapter;

import javax.inject.Inject;

public class LoginActivity extends BaseActivity<ActivityLoginBinding, LoginPresenter> implements LoginNavigator, GoogleApiClient.OnConnectionFailedListener {

    @Inject
    LoginPresenter mLoginPresenter;

    private ActivityLoginBinding mActivityLoginBinding;

    private GoogleApiClient mGoogleApiClient;
    public static final int RC_SIGN_IN = 9001;

    private static final int REQUEST_SIGNUP = 0;

    @Override
    public int getBindingVariable() {
        return BR.presenter;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    public LoginPresenter getPresenter() {
        return mLoginPresenter;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityLoginBinding = getViewDataBinding();
        mLoginPresenter.setNavigator(this);

        setUp();
    }

    private void setUp() {

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        mActivityLoginBinding.btGoogleSignin.setOnClickListener(v -> {
            mLoginPresenter.setIsLoading(true);
            Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
            startActivityForResult(signInIntent, RC_SIGN_IN);
        });

        mActivityLoginBinding.lytSignUp.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), SignupActivity.class);
            startActivityForResult(intent, REQUEST_SIGNUP);
            overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
        });

        TextWatcherAdapter watcher = new TextWatcherAdapter() {
            @Override public void afterTextChanged(Editable s) {
                mLoginPresenter.validate(getResources());
            }
        };

        mActivityLoginBinding.etEmail.addTextChangedListener(watcher);
        mActivityLoginBinding.etPassword.addTextChangedListener(watcher);


        mActivityLoginBinding.btSignIn.setOnClickListener(v -> {
            mLoginPresenter.loginExecuted = true;
            if (mLoginPresenter.validate(getResources())) {
                mLoginPresenter.loginEmailPassword();
            }
        });
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (result.isSuccess()) {
                GoogleSignInAccount account = result.getSignInAccount();
                mLoginPresenter.loginWithGoogle(account, this);
            } else {
                onError("Authentication Failed");
            }
        }

        if (requestCode == REQUEST_SIGNUP) {
            if (resultCode == RESULT_OK) {
                onLoginSuccessful(null);
            }
        }
    }

    @Override
    public void onLoginSuccessful(FirebaseUser user) {
        mLoginPresenter.getPreferencesHelper().setIsLoggedIn(true);
        if(mLoginPresenter.getPreferencesHelper().getIsFirstTimeLaunch()) {
            newActivity(WelcomeActivity.class);
        }else {
            newActivity(MainActivity.class);
        }
    }

    @Override
    public void onError(String error) {
        Toast.makeText(this,error,Toast.LENGTH_LONG).show();
    }
}
