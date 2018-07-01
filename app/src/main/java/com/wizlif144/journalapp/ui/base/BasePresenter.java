

package com.wizlif144.journalapp.ui.base;

import android.arch.lifecycle.ViewModel;
import android.databinding.ObservableBoolean;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.wizlif144.journalapp.data.prefs.PreferencesHelper;
import com.wizlif144.journalapp.utils.rx.SchedulerProvider;

import io.reactivex.disposables.CompositeDisposable;

public abstract class BasePresenter<N> extends ViewModel {


    private final FirebaseAuth mAuthManager;

    private final FirebaseDatabase mDatabaseManager;
//
//    private final EzyAgricService mEzyAgricService;

    private final ObservableBoolean mIsLoading = new ObservableBoolean(false);

    private final ObservableBoolean mIsSyncing = new ObservableBoolean(false);

    private CompositeDisposable mCompositeDisposable;

    private final SchedulerProvider mSchedulerProvider;
//
    private PreferencesHelper mPreferencesHelper;

    private N mNavigator;

    public BasePresenter(FirebaseDatabase firebaseDatabase,
                         FirebaseAuth firebaseAuth,
                         PreferencesHelper preferencesHelper,
                         SchedulerProvider schedulerProvider) {
        this.mAuthManager = firebaseAuth;
        this.mDatabaseManager = firebaseDatabase;
        this.mCompositeDisposable = new CompositeDisposable();
        this.mSchedulerProvider = schedulerProvider;
        this.mPreferencesHelper = preferencesHelper;
    }

    @Override
    protected void onCleared() {
        mCompositeDisposable.dispose();
        super.onCleared();
    }

    public CompositeDisposable getCompositeDisposable() {
        return mCompositeDisposable;
    }

    public FirebaseDatabase getDataManager() {
        return mDatabaseManager;
    }

    public FirebaseAuth getAuthManager() {
        return mAuthManager;
    }


    public ObservableBoolean getIsLoading() {
        return mIsLoading;
    }

    public ObservableBoolean getIsSyncing() {
        return mIsSyncing;
    }

    public void setIsLoading(boolean isLoading) {
        mIsLoading.set(isLoading);
    }

    public void setIsSyncing(boolean isSyncing) {
        mIsSyncing.set(isSyncing);
    }


    public N getNavigator() {
        return mNavigator;
    }

    public void setNavigator(N navigator) {
        this.mNavigator = navigator;
    }

    public PreferencesHelper getPreferencesHelper(){return  this.mPreferencesHelper;}

    public SchedulerProvider getSchedulerProvider() {
        return mSchedulerProvider;
    }
}
