package com.wizlif144.journalapp.ui.main.main.notes;

import android.databinding.ObservableField;
import android.graphics.Bitmap;

import com.wizlif144.journalapp.data.models.Note;
import com.wizlif144.journalapp.utils.CommonUtils;

import java.util.Date;

public class NoteItemPresenter {

    public ObservableField<String> title = new ObservableField<>();
    public ObservableField<String> body = new ObservableField<>();
    public ObservableField<String> date = new ObservableField<>();
    public ObservableField<String> day = new ObservableField<>();
    public ObservableField<String> weather = new ObservableField<>();
    public ObservableField<Bitmap> image = new ObservableField<>();
    public ObservableField<Date> rawDate = new ObservableField<>();
    public ObservableField<Boolean> isPictureAvailable = new ObservableField<>(false);

    public NoteItemPresenterListener mListener;

    public NoteItemPresenter(Note note,NoteItemPresenterListener listener){
        this.mListener = listener;
        this.title.set(note.getTitle());
        this.body.set(note.getBody());
        Date parsedDate = CommonUtils.toDate(note.getDate(),"yyyy-MM-dd");
        this.date.set(CommonUtils.zero(parsedDate.getDate()));
        this.day.set(CommonUtils.dateToString(parsedDate,"EEE"));
        this.weather.set(note.getWeather());
        this.rawDate.set(parsedDate);
        this.image.set(CommonUtils.stringToImage(note.getImage()));

        if(this.image.get() != null){
            this.isPictureAvailable.set(true);
        }
    }

    public void onItemSelected(){
        this.mListener.onItemSelected();
    }

}
