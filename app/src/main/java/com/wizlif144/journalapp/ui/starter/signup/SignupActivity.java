package com.wizlif144.journalapp.ui.starter.signup;

import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseUser;
import com.wizlif144.journalapp.BR;
import com.wizlif144.journalapp.R;
import com.wizlif144.journalapp.databinding.ActivitySignUpBinding;
import com.wizlif144.journalapp.ui.base.BaseActivity;
import com.wizlif144.journalapp.utils.binding.TextWatcherAdapter;

import javax.inject.Inject;

public class SignupActivity extends BaseActivity<ActivitySignUpBinding, SignupPresenter> implements SignupNavigator {

    @Inject
    SignupPresenter mSignupPresenter;

    private ActivitySignUpBinding mActivitySignUpBinding;

    @Override
    public int getBindingVariable() {
        return BR.presenter;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_sign_up;
    }

    @Override
    public SignupPresenter getPresenter() {
        return mSignupPresenter;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivitySignUpBinding = getViewDataBinding();
        mSignupPresenter.setNavigator(this);

        setUp();
    }

    private void setUp(){

        TextWatcherAdapter watcher = new TextWatcherAdapter() {
            @Override public void afterTextChanged(Editable s) {
                mSignupPresenter.validate(getResources());
            }
        };

        mActivitySignUpBinding.etEmail.addTextChangedListener(watcher);
        mActivitySignUpBinding.etPassword.addTextChangedListener(watcher);
        mActivitySignUpBinding.etConfirmPassword.addTextChangedListener(watcher);

        mActivitySignUpBinding.btSignUp.setOnClickListener(v -> {
            mSignupPresenter.loginExecuted = true;
            if (mSignupPresenter.validate(getResources())) {
                mSignupPresenter.signup(SignupActivity.this);
            }
        });

        mActivitySignUpBinding.btnLogin.setOnClickListener(v -> finish());
    }

    @Override
    public void onSignupSuccessful(FirebaseUser user) {
        Toast.makeText(this,"Account created.",Toast.LENGTH_LONG).show();
        setResult(RESULT_OK, null);
        finish();
    }

    @Override
    public void onError(String error) {
        Toast.makeText(this,error,Toast.LENGTH_LONG).show();
    }
}
