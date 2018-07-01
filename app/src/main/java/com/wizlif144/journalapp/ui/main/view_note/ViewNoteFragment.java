package com.wizlif144.journalapp.ui.main.view_note;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;

import com.wizlif144.journalapp.BR;
import com.wizlif144.journalapp.R;
import com.wizlif144.journalapp.databinding.FragmentViewNoteBinding;
import com.wizlif144.journalapp.ui.base.BaseFragment;
import com.wizlif144.journalapp.ui.main.MainActivityNavigator;
import com.wizlif144.journalapp.ui.main.MainActivityPresenter;
import com.wizlif144.journalapp.ui.main.main.notes.NoteItemPresenter;
import com.wizlif144.journalapp.utils.CommonUtils;

import javax.inject.Inject;

public class ViewNoteFragment extends BaseFragment<FragmentViewNoteBinding, MainActivityPresenter> implements MainActivityNavigator {

    private FragmentViewNoteBinding mFragmentViewNoteBinding;

    @Inject
    MainActivityPresenter mMainPresenter;

    public static String TAG = ViewNoteFragment.class.getSimpleName();

    public static ViewNoteFragment newInstance() {
        return new ViewNoteFragment();
    }

    @Override
    public int getBindingVariable() {
        return BR.presenter;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_view_note;
    }

    @Override
    public MainActivityPresenter getPresenter() {
        return mMainPresenter;
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mFragmentViewNoteBinding = getViewDataBinding();
        setUp();
    }

    private void setUp() {
        mMainPresenter.setNavigator(this);

        if(mMainPresenter.selectedItem != null){
            mFragmentViewNoteBinding.etTitle.setText(mMainPresenter.selectedItem.title.get());
            mFragmentViewNoteBinding.etBody.setText(mMainPresenter.selectedItem.body.get());
            mFragmentViewNoteBinding.tvDate.setText(CommonUtils.dateToString(mMainPresenter.selectedItem.rawDate.get(),"EEE, MMM dd yyyy"));
            getWeather(mFragmentViewNoteBinding.ivWeather,mMainPresenter.selectedItem.weather.get());
        }

        mFragmentViewNoteBinding.btnBack.setOnClickListener(v ->  getBaseActivity().onFragmentDetached(TAG));
    }

    public static void getWeather(ImageView imageView, String weather){
        switch (weather) {
            case "RAINY":
                imageView.setImageDrawable(ContextCompat.getDrawable(imageView.getContext(), R.drawable.ic_rainy));
                break;
            case "SNOWY":
                imageView.setImageDrawable(ContextCompat.getDrawable(imageView.getContext(), R.drawable.ic_snowy));
                break;
            case "SUNNY":
                imageView.setImageDrawable(ContextCompat.getDrawable(imageView.getContext(), R.drawable.ic_sunny));
                break;
            case "CLOUDY":
                imageView.setImageDrawable(ContextCompat.getDrawable(imageView.getContext(), R.drawable.ic_partly_cloudy));
                break;
            case "LIGHTENING":
                imageView.setImageDrawable(ContextCompat.getDrawable(imageView.getContext(), R.drawable.ic_lightning));
                break;
        }
    }

    @Override
    public void handleError(String error) {

    }

    @Override
    public void handleSuccess(String message) {

    }

    @Override
    public void onNoteSelected(NoteItemPresenter note) {

    }

    @Override
    public void onResume() {
        super.onResume();

        if (getView() == null) {
            return;
        }

        getView().setFocusableInTouchMode(true);
        getView().requestFocus();
        getView().setOnKeyListener((v, keyCode, event) -> {
            if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK) {
                getBaseActivity().onFragmentDetached(TAG);
                return  true;
            }
            return false;
        });

    }
}
