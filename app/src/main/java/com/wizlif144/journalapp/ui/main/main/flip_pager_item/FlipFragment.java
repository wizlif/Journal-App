package com.wizlif144.journalapp.ui.main.main.flip_pager_item;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.CalendarView;

import com.wajahatkarim3.easyflipview.EasyFlipView;
import com.wizlif144.journalapp.BR;
import com.wizlif144.journalapp.R;
import com.wizlif144.journalapp.data.models.Note;
import com.wizlif144.journalapp.databinding.FragmentPagerItemBinding;
import com.wizlif144.journalapp.ui.base.BaseFragment;
import com.wizlif144.journalapp.ui.main.MainActivityNavigator;
import com.wizlif144.journalapp.ui.main.MainActivityPresenter;
import com.wizlif144.journalapp.ui.main.main.notes.NoteItemPresenter;
import com.wizlif144.journalapp.ui.main.main.notes.NotesFragment;
import com.wizlif144.journalapp.utils.AppLogger;
import com.wizlif144.journalapp.utils.CommonUtils;

import java.util.Calendar;
import java.util.Date;

import javax.inject.Inject;

public class FlipFragment extends BaseFragment<FragmentPagerItemBinding, MainActivityPresenter> implements MainActivityNavigator {

    private FragmentPagerItemBinding mFragmentPagerItemBinding;

    private EasyFlipView mFlipView;

    private String mMonth, mDate;

    private int month,date,year;

    @Inject
    MainActivityPresenter mMainPresenter;

    public static String TAG = FlipFragment.class.getSimpleName();

    public static FlipFragment newInstance(String date, String month) {
        Bundle args = new Bundle();
        args.putString("month", month);
        args.putString("date", date);
        FlipFragment fragment = new FlipFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getBindingVariable() {
        return BR.presenter;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_pager_item;
    }

    @Override
    public MainActivityPresenter getPresenter() {
        return mMainPresenter;
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mFragmentPagerItemBinding = getViewDataBinding();
        setUp();
    }

    private void setUp() {
        mMainPresenter.setNavigator(this);
        mFlipView = mFragmentPagerItemBinding.getRoot().findViewById(R.id.flip_view);

        mFragmentPagerItemBinding.incCardCalendar.calendar.setOnDateChangeListener((view, year, month, dayOfMonth) -> {
            mMainPresenter.selectedDate.set(CommonUtils.getDate(year, month, dayOfMonth));
            this.month = month;
            this.year = year;
            this.date = dayOfMonth;
            mFragmentPagerItemBinding.incCardMonth.tvDate.setText(String.valueOf(dayOfMonth));
            mFragmentPagerItemBinding.incCardMonth.tvMonth.setText(CommonUtils.months[month]);
            mMainPresenter.setSelectedDateDisplayValue();
        });

        mFragmentPagerItemBinding.incCardMonth.lytParent.setOnClickListener(view -> {
            mMainPresenter.selectedDate.set(getFullDate());
            newFragment(NotesFragment.newInstance(),NotesFragment.TAG);
        });

        Bundle bundle = getArguments();
        if (bundle != null) {
            mMonth = bundle.containsKey("month") ? bundle.getString("month") : "";
            mDate = bundle.containsKey("date") ? bundle.getString("date") : "";

            mFragmentPagerItemBinding.incCardMonth.tvDate.setText(mDate);
            mFragmentPagerItemBinding.incCardMonth.tvMonth.setText(mMonth);

            year = Integer.valueOf(mMainPresenter.mYear.get());
            month = CommonUtils.find(CommonUtils.months,mMonth);
            date = Integer.valueOf(mDate);

            Calendar firstDate=Calendar.getInstance();
            firstDate.set(year, month, 1, 0, 0, 0);

            mFragmentPagerItemBinding.incCardCalendar.calendar.setMinDate(firstDate.getTimeInMillis());

            Calendar lastDate= Calendar.getInstance();
            lastDate.set(year, month+1, 0, 0, 0, 0);

            mFragmentPagerItemBinding.incCardCalendar.calendar.setMaxDate(lastDate.getTimeInMillis());

            Calendar currentDate= Calendar.getInstance();
            currentDate.set(year, month, date, 0, 0, 0);

            mFragmentPagerItemBinding.incCardCalendar.calendar.setDate(currentDate.getTimeInMillis());
        } else {
            handleError("Failed to instantiate.");
        }
    }

    public void flip() {
        mFlipView.flipTheView();
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

    public int getYear(){ return year;}
    public int getMonth(){return month;}
    public int getDate(){return date;}
    public Date getFullDate(){ return CommonUtils.getDate(year,month,date);}
}
