/*
 *  Copyright (C) 2017 MINDORKS NEXTGEN PRIVATE LIMITED
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      https://mindorks.com/license/apache-v2
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License
 */

package com.wizlif144.journalapp.ui.main.main;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.wizlif144.journalapp.ui.main.main.flip_pager_item.FlipFragment;
import com.wizlif144.journalapp.utils.AppLogger;
import com.wizlif144.journalapp.utils.CommonUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;



public class MonthsPagerAdapter extends FragmentPagerAdapter {

    private List<Fragment> fragments;
    private List<Date> dateList;

    public MonthsPagerAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
        fragments = new ArrayList<>();
        dateList = new ArrayList<>();
    }

    @Override
    public int getCount() {
        return fragments.size();
    }


    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    public void addFragment(Fragment fragment){
        fragments.add(fragment);
    }

    public void clearItems(){
        fragments = new ArrayList<>();
    }

    public void addItems(List<Date> dates){
        fragments.clear();
        for(Date date : dates){
            dateList.add(date);
            fragments.add(FlipFragment.newInstance(
                    CommonUtils.zero(date.getDate()),
                    CommonUtils.months[date.getMonth()]));
        }
        notifyDataSetChanged();
    }
}
