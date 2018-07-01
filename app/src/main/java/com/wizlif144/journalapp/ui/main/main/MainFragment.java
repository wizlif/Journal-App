package com.wizlif144.journalapp.ui.main.main;

import android.content.res.ColorStateList;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.view.KeyEvent;
import android.view.View;

import com.wizlif144.journalapp.BR;
import com.wizlif144.journalapp.R;
import com.wizlif144.journalapp.data.models.Note;
import com.wizlif144.journalapp.databinding.FragmentMainBinding;
import com.wizlif144.journalapp.ui.base.BaseFragment;
import com.wizlif144.journalapp.ui.main.MainActivityNavigator;
import com.wizlif144.journalapp.ui.main.MainActivityPresenter;
import com.wizlif144.journalapp.ui.main.main.flip_pager_item.FlipFragment;
import com.wizlif144.journalapp.ui.main.main.notes.NoteItemPresenter;
import com.wizlif144.journalapp.ui.main.new_note.NewNoteFragment;
import com.wizlif144.journalapp.utils.CommonUtils;

import java.util.Calendar;
import java.util.Date;

import javax.inject.Inject;

public class MainFragment extends BaseFragment<FragmentMainBinding,MainActivityPresenter> implements MainActivityNavigator {

    private FragmentMainBinding mFragmentMainBinding;

    @Inject
    MainActivityPresenter mMainPresenter;

    @Inject
    MonthsPagerAdapter mMonthsPagerAdapter;

    private boolean flipped = false;


    public static String TAG = MainFragment.class.getSimpleName();

    public static MainFragment newInstance() {
        return new MainFragment();
    }

    @Override
    public int getBindingVariable() {return BR.presenter;}

    @Override
    public int getLayoutId() {
        return R.layout.fragment_main;
    }

    @Override
    public MainActivityPresenter getPresenter() {
        return mMainPresenter;
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mFragmentMainBinding = getViewDataBinding();
        setUp();
    }

    private void setUp() {
        mMainPresenter.setNavigator(this);
        mFragmentMainBinding.viewPager.setAdapter(mMonthsPagerAdapter);
        mFragmentMainBinding.viewPager.setOffscreenPageLimit(11);
        mMainPresenter.initializeDates(Calendar.getInstance().get(Calendar.YEAR));

//        AppLogger.e(String.valueOf(new Date().getMonth()));
        mFragmentMainBinding.viewPager.setPageMargin(getResources().getDimensionPixelOffset(R.dimen.viewpager_margin_overlap));

        mFragmentMainBinding.fabCalendar.setOnClickListener(v -> {
            if(!flipped) {
                mFragmentMainBinding.fabCalendar.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.ic_back));
                mFragmentMainBinding.fabCalendar.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(getContext(), R.color.primary)));
                flipAll();
                flipped = true;
            }else{
                mFragmentMainBinding.fabCalendar.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.ic_calendar));
                mFragmentMainBinding.fabCalendar.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(getContext(), R.color.grey_80)));
                flipAll();
                flipped = false;
            }
        });

        mFragmentMainBinding.lytToday.setOnClickListener(view -> mFragmentMainBinding.viewPager.setCurrentItem(new Date().getMonth()));

        mFragmentMainBinding.fabNewNote.setOnClickListener(view -> {
            FlipFragment fragment = ((FlipFragment) mMonthsPagerAdapter.getItem(mFragmentMainBinding.viewPager.getCurrentItem()));
            mMainPresenter.selectedDate.set(fragment.getFullDate());
            mMainPresenter.setSelectedDateDisplayValue();
            newFragment(NewNoteFragment.newInstance(),NewNoteFragment.TAG);
        });

        mFragmentMainBinding.viewPager.postDelayed(() -> mFragmentMainBinding.viewPager.setCurrentItem(new Date().getMonth()), 100);
    }

    private void flipAll(){
        for(int i = 0; i< 12; i++){
            ((FlipFragment) mMonthsPagerAdapter.getItem(i)).flip();
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
