package com.wizlif144.journalapp.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.util.Base64;
import android.view.Gravity;
import android.view.View;

import com.wizlif144.journalapp.R;
import com.wizlif144.journalapp.data.enums.POSITION;
import com.wizlif144.journalapp.data.enums.STATUS;

import java.io.ByteArrayOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import static com.wizlif144.journalapp.data.enums.POSITION.TOP;

public final class CommonUtils {
    private CommonUtils() {
    }

    public static String[] months = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};

    public static Date getDate(int year, int month, int day) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month);
        cal.set(Calendar.DAY_OF_MONTH, day);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    public static String zero(int number) {
        if (number < 10) {
            return String.format(Locale.US, "0%d", number);
        } else {
            return String.valueOf(number);
        }
    }

    public static int find(String[] a, String target) {
        for (int i = 0; i < a.length; i++)
            if (a[i].equals(target))
                return i;

        return -1;
    }

    public static String bitMapToBase64(Bitmap bmp) {
        if (bmp != null) {
            ByteArrayOutputStream bao = new ByteArrayOutputStream();
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, bao);
            bmp.recycle();
            byte[] byteArray = bao.toByteArray();
            return Base64.encodeToString(byteArray, Base64.DEFAULT);
        } else {
            return null;
        }
    }


    public static void snackbar(View v, String message, STATUS style, POSITION position, int length) {
        if (length == -1) {
            length = Snackbar.LENGTH_LONG;
        }
        int color;

        switch (style) {
            case ERROR:
                color = R.color.red_500;
                break;
            case SUCCESS:
                color = R.color.green_A700;
                break;
            default:
                color = R.color.blue_500;
                break;

        }

        Snackbar snackbar = Snackbar.make(v, message, length);
        View view = snackbar.getView();
        view.setBackgroundColor(ContextCompat.getColor(v.getContext(), color));
        if (position == TOP) {
            CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) view.getLayoutParams();
            params.gravity = Gravity.TOP;
            view.setLayoutParams(params);
        }

        snackbar.show();

    }

    public static Date toDate(String date,String format){
        try {
            return new SimpleDateFormat(format, Locale.US).parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String dateToString(Date date, String format){
        try {
            return new SimpleDateFormat(format, Locale.US).format(date);
        }catch (Exception e){
            return null;
        }
    }

    public static Bitmap stringToImage(String base64){
        try {
            byte[] decodedString = Base64.decode(base64, Base64.DEFAULT);
            return BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        }catch (Exception e){
            return null;
        }
    }

}

