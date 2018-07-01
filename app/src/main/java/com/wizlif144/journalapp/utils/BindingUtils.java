package com.wizlif144.journalapp.utils;

import android.databinding.BindingAdapter;
import android.graphics.Bitmap;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;

import com.wizlif144.journalapp.R;
import com.wizlif144.journalapp.data.enums.Weather;
import com.wizlif144.journalapp.data.models.Note;
import com.wizlif144.journalapp.ui.main.main.MonthsPagerAdapter;
import com.wizlif144.journalapp.ui.main.main.notes.NotesAdapter;

import java.util.Date;
import java.util.List;
import java.util.Locale;

import static com.wizlif144.journalapp.data.enums.Weather.RAINY;

public final class BindingUtils {

    private BindingUtils() {
    }


    @BindingAdapter({"imageBmp"})
    public static void setImageBitmap(ImageView imageView, Bitmap bitmap) {
        imageView.setImageBitmap(bitmap);
    }

    @BindingAdapter({"imageBmp"})
    public static void setStringImageBitmap(ImageView imageView, String bitmap) {
        Bitmap image = CommonUtils.stringToImage(bitmap);
         if(image != null)  imageView.setImageBitmap(image);
    }


    @BindingAdapter({"date"})
    public static void setTitleDate(TextView textView, Date date) {
        textView.setText(CommonUtils.dateToString(date,"MMMM / yyyy"));
    }

    @BindingAdapter({"dateLong"})
    public static void setLongDate(TextView textView, Date date) {
        textView.setText(CommonUtils.dateToString(date,"EEE, MMM dd yyyy"));
    }


    @BindingAdapter({"textColor"})
    public static void setTextColor(TextView textView, String day) {
        if(day.equals("Sun")) {
            textView.setTextColor(ContextCompat.getColor(textView.getContext(), R.color.colorAccent));
        }
    }

    @BindingAdapter({"adapter"})
    public static void addMonthItems(ViewPager viewPager, List<Date> dates) {
        MonthsPagerAdapter adapter = (MonthsPagerAdapter) viewPager.getAdapter();
        if (adapter != null) {
            adapter.clearItems();
            adapter.addItems(dates);
        }
    }

    @BindingAdapter({"adapter"})
    public static void addNotesItems(RecyclerView recyclerView, List<Note> noteList) {
        NotesAdapter adapter = (NotesAdapter) recyclerView.getAdapter();
        if (adapter != null) {
            adapter.clearItems();
            adapter.addItems(noteList);
        }
    }

    @BindingAdapter({"weather"})
    public static void setWeather(ImageView imageView, Weather weather) {
        switch (weather) {
            case RAINY:
                imageView.setImageDrawable(ContextCompat.getDrawable(imageView.getContext(), R.drawable.ic_rainy));
                break;
            case SNOWY:
                imageView.setImageDrawable(ContextCompat.getDrawable(imageView.getContext(), R.drawable.ic_snowy));
                break;
            case SUNNY:
                imageView.setImageDrawable(ContextCompat.getDrawable(imageView.getContext(), R.drawable.ic_sunny));
                break;
            case CLOUDY:
                imageView.setImageDrawable(ContextCompat.getDrawable(imageView.getContext(), R.drawable.ic_partly_cloudy));
                break;
            case LIGHTENING:
                imageView.setImageDrawable(ContextCompat.getDrawable(imageView.getContext(), R.drawable.ic_lightning));
                break;
        }
    }

    @BindingAdapter({"weather"})
    public static void setStringWeather(ImageView imageView, String weather) {
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

}