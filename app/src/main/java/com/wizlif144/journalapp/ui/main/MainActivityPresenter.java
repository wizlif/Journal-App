package com.wizlif144.journalapp.ui.main;

import android.databinding.ObservableArrayList;
import android.databinding.ObservableField;
import android.databinding.ObservableList;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.wizlif144.journalapp.data.enums.Weather;
import com.wizlif144.journalapp.data.models.Note;
import com.wizlif144.journalapp.data.prefs.PreferencesHelper;
import com.wizlif144.journalapp.ui.base.BasePresenter;
import com.wizlif144.journalapp.ui.main.main.notes.NoteItemPresenter;
import com.wizlif144.journalapp.ui.main.main.notes.NotesFragment;
import com.wizlif144.journalapp.ui.starter.login.LoginNavigator;
import com.wizlif144.journalapp.utils.CommonUtils;
import com.wizlif144.journalapp.utils.rx.SchedulerProvider;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;

public class MainActivityPresenter extends BasePresenter<MainActivityNavigator> {

    public MainActivityPresenter(FirebaseDatabase firebaseDatabase, FirebaseAuth firebaseAuth, PreferencesHelper preferencesHelper, SchedulerProvider schedulerProvider) {
        super(firebaseDatabase, firebaseAuth, preferencesHelper, schedulerProvider);
    }

    public ObservableList<Date> mMonths = new ObservableArrayList<>();

    public ObservableField<String> mYear = new ObservableField<>(String.valueOf(Calendar.getInstance().get(Calendar.YEAR)));

    public ObservableField<String> today = new ObservableField<>(new SimpleDateFormat("MMM, dd / yyyy", Locale.US).format(new Date()));

    public ObservableField<Date> selectedDate = new ObservableField<>();
    public ObservableField<String> selectedDateDisplayValue = new ObservableField<>();

    public ObservableField<Weather> selectedWeather = new ObservableField<>(Weather.CLOUDY);

    public ObservableList<Note> mNotesList = new ObservableArrayList<>();

    public NoteItemPresenter selectedItem;

    public void initializeDates(int year) {
        List<Date> dateList = new ArrayList<>();
        getCompositeDisposable().add(
                generateDates(year)
                        .subscribeOn(getSchedulerProvider().io())
                        .observeOn(getSchedulerProvider().ui())
                        .doOnNext(dateList::add)
                        .doOnComplete(() -> mMonths.addAll(dateList))
                        .subscribe(t -> {
                        }, e -> getNavigator().handleError("Failed to initiate dates."))
        );
    }

    private Observable<Date> generateDates(final int year) {
        return Observable.create(emitter -> {
            try {
                Date date = new Date();
                for (int i = 1; i <= 12; i++) {
                    if (date.getMonth() == (i - 1)) {
                        emitter.onNext(CommonUtils.getDate(year, i - 1, date.getDate()));
                    } else {
                        emitter.onNext(CommonUtils.getDate(year, i - 1, i));
                    }
                }
                emitter.onComplete();
            } catch (Exception e) {
                emitter.onError(e);
            }
        });
    }

    public void setSelectedDateDisplayValue() {
        this.selectedDateDisplayValue.set(new SimpleDateFormat("EEE, MMM dd yyyy", Locale.US).format(this.selectedDate.get()));
    }

    public String getSelectedDateTimeStamp() {
        return new SimpleDateFormat("yyyy-MM-dd", Locale.US).format(this.selectedDate.get());
    }

    public void saveNote(Note note) {
        getDataManager()
                .getReference()
                .child("notes")
                .child(getAuthManager().getCurrentUser().getUid())
                .push()
                .setValue(note)
                .addOnSuccessListener(aVoid -> getNavigator().handleSuccess("Saved."))
                .addOnFailureListener(e -> getNavigator().handleError(e.getMessage()));
        getNavigator().handleSuccess("Saved.");
        mNotesList.add(note);
    }

    public void fetchNotes() {
//        if(mNotesList.isEmpty()) {
//            setIsLoading(true);
//        mNotesList.clear();
            getCompositeDisposable().add(
                    notesFetcher(String.valueOf(selectedDate.get().getMonth()))
                            .subscribeOn(getSchedulerProvider().io())
                            .observeOn(getSchedulerProvider().ui())
                            .doOnNext(note -> mNotesList.add(note))
                            .subscribe(t -> {
                            }, e -> {
//                                setIsLoading(false);
                                getNavigator().handleError("Failed to fetch notes");
                            })
            );
//        }
    }

    //    .startAt(mYear.get() + "-" + month + "-01")
//            .endAt(mYear.get() + "-" + month + "-31")
//            .limitToFirst(31)
//                .orderByChild("date")
    public Observable<Note> notesFetcher(String month) {
        final ObjectMapper mapper = new ObjectMapper(); // jackson's objectmapper
        return Observable.create(emitter -> getDataManager()
                .getReference()
                .child("notes")
                .child(getAuthManager().getCurrentUser().getUid())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                            emitter.onNext(mapper.convertValue(postSnapshot.getValue(), Note.class));
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        emitter.onError(databaseError.toException());
                    }
                }));
    }
}
