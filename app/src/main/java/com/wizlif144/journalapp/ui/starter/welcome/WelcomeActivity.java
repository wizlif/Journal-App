package com.wizlif144.journalapp.ui.starter.welcome;

import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wizlif144.journalapp.R;
import com.wizlif144.journalapp.data.prefs.PreferencesHelper;
import com.wizlif144.journalapp.ui.main.MainActivity;

import javax.inject.Inject;

import dagger.android.AndroidInjection;

public class WelcomeActivity extends AppCompatActivity {

    @Inject
    PreferencesHelper preferencesHelper;

    ViewPager.OnPageChangeListener mOnPageChangeListener = new PageChangeListener(this);

    private ViewPager mViewPager;

    private WelcomeAdapter mAdapter;

    private String[] headers = new String[]{"Ready to Travel", "Important Stuff", "Flight to Destination", "Enjoy Holiday"};
    private String[] sub_headers = new String[]{"Choose your destination, plan Your trip. Enjoy and record every moment of it.",
            "Don't worry. We give you the best way to save it. We guarantee!", "Safe and Comfort flight is our priority. Professional crew and services.", "Enjoy your holiday, Don't forget to feel the moment and take a photo!"};
    private int[] images = new int[]{R.drawable.img_wizard_1, R.drawable.img_wizard_2, R.drawable.img_wizard_3, R.drawable.img_wizard_4};
    private int[] backgrounds = new int[]{R.drawable.image_15, R.drawable.image_10, R.drawable.image_3, R.drawable.image_12};


    class PageChangeListener implements ViewPager.OnPageChangeListener {
        final /* synthetic */ WelcomeActivity welcomeActivity;

        PageChangeListener(WelcomeActivity welcomeActivity) {
            this.welcomeActivity = welcomeActivity;
        }

        public void onPageSelected(int i) {
            this.welcomeActivity.pageChanger(i);
        }

        public void onPageScrolled(int i, float f, int i2) {
        }

        public void onPageScrollStateChanged(int i) {

        }
    }

    private void pageChanger(int i) {
        LinearLayout linearLayout = findViewById(R.id.layoutDots);
        ImageView[] imageViewArr = new ImageView[4];
        linearLayout.removeAllViews();
        for (int i2 = 0; i2 < imageViewArr.length; i2++) {
            imageViewArr[i2] = new ImageView(this);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(new ViewGroup.LayoutParams(15, 15));

            layoutParams.setMargins(10, 10, 10, 10);
            imageViewArr[i2].setLayoutParams(layoutParams);
            imageViewArr[i2].setImageResource(R.drawable.shape_circle);
            imageViewArr[i2].setColorFilter(getResources().getColor(R.color.grey_20), PorterDuff.Mode.SRC_IN);
            linearLayout.addView(imageViewArr[i2]);
        }
        imageViewArr[i].setImageResource(R.drawable.shape_circle);
        imageViewArr[i].setColorFilter(getResources().getColor(R.color.light_blue_600), PorterDuff.Mode.SRC_IN);
    }

    public class WelcomeAdapter extends PagerAdapter {
        final /* synthetic */ WelcomeActivity welcomeActivity;
        private LayoutInflater layoutInflater;
        private Button button;

        class ClickListener implements View.OnClickListener {
            final /* synthetic */ WelcomeAdapter welcomeAdapter;

            ClickListener(WelcomeAdapter adapter) {
                this.welcomeAdapter = adapter;
            }

            public void onClick(View view) {
                int currentItem = this.welcomeAdapter.welcomeActivity.mViewPager.getCurrentItem() + 1;
                if (currentItem < 4) {
                    this.welcomeAdapter.welcomeActivity.mViewPager.setCurrentItem(currentItem);
                } else {
                    preferencesHelper.setIsFirstTimeLaunch(false);
                    this.welcomeAdapter.welcomeActivity.startActivity(new Intent(this.welcomeAdapter.welcomeActivity, MainActivity.class));
                    this.welcomeAdapter.welcomeActivity.finish();
                }
            }
        }

        public WelcomeAdapter(WelcomeActivity welcomeActivity) {
            this.welcomeActivity = welcomeActivity;
        }

        public Object instantiateItem(ViewGroup viewGroup, int i) {
            this.layoutInflater = (LayoutInflater) this.welcomeActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View inflate = this.layoutInflater.inflate(R.layout.item_card_wizard_bg, viewGroup, false);
            ((TextView) inflate.findViewById(R.id.title)).setText(this.welcomeActivity.headers[i]);
            ((TextView) inflate.findViewById(R.id.description)).setText(this.welcomeActivity.sub_headers[i]);
            ((ImageView) inflate.findViewById(R.id.image)).setImageResource(this.welcomeActivity.images[i]);
            ((ImageView) inflate.findViewById(R.id.image_bg)).setImageResource(this.welcomeActivity.backgrounds[i]);
            this.button = inflate.findViewById(R.id.btn_next);
            if (i == this.welcomeActivity.headers.length - 1) {
                this.button.setText("Get Started");
            } else {
                this.button.setText("Next");
            }
            this.button.setOnClickListener(new ClickListener(this));
            viewGroup.addView(inflate);
            return inflate;
        }

        public void destroyItem(ViewGroup viewGroup, int i, Object obj) {
            viewGroup.removeView((View) obj);
        }

        public boolean isViewFromObject(View view, Object obj) {
            return view == obj;
        }

        public int getCount() {
            return this.welcomeActivity.headers.length;
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AndroidInjection.inject(this);
        setContentView(R.layout.activity_welcome);
        this.mViewPager = findViewById(R.id.view_pager);
        pageChanger(0);
        this.mAdapter = new WelcomeAdapter(this);
        this.mViewPager.setAdapter(this.mAdapter);
        this.mViewPager.addOnPageChangeListener(this.mOnPageChangeListener);

        if (Build.VERSION.SDK_INT >= 21) {
            Window window = getWindow();
            window.addFlags(Integer.MIN_VALUE);
            window.clearFlags(67108864);
            window.setStatusBarColor(getResources().getColor(R.color.grey_20));
        }
    }
}
