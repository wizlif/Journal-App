package com.wizlif144.journalapp.ui.main.new_note;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetDialogFragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.wizlif144.journalapp.BR;
import com.wizlif144.journalapp.R;
import com.wizlif144.journalapp.data.enums.PictureMode;
import com.wizlif144.journalapp.data.enums.STATUS;
import com.wizlif144.journalapp.data.enums.Weather;
import com.wizlif144.journalapp.data.models.Note;
import com.wizlif144.journalapp.databinding.FragmentNewNoteBinding;
import com.wizlif144.journalapp.databinding.ListPictureSelectionBinding;
import com.wizlif144.journalapp.databinding.ListWeatherBinding;
import com.wizlif144.journalapp.ui.base.BaseFragment;
import com.wizlif144.journalapp.ui.main.MainActivityNavigator;
import com.wizlif144.journalapp.ui.main.MainActivityPresenter;
import com.wizlif144.journalapp.ui.main.main.flip_pager_item.FlipFragment;
import com.wizlif144.journalapp.ui.main.main.notes.NoteItemPresenter;
import com.wizlif144.journalapp.ui.main.main.notes.NotesFragment;
import com.wizlif144.journalapp.utils.CommonUtils;

import java.io.IOException;

import javax.inject.Inject;

public class NewNoteFragment extends BaseFragment<FragmentNewNoteBinding, MainActivityPresenter> implements MainActivityNavigator,WeatherSelector,PictureModeSelector {

    private FragmentNewNoteBinding mFragmentNewNoteBinding;

    @Inject
    MainActivityPresenter mMainPresenter;

    WeatherBottomSheetFragment weatherBottomSheetFragment;
    PictureBottomSheetFragment pictureBottomSheetFragment;

    Bitmap image;

    public static String TAG = FlipFragment.class.getSimpleName();
    public static int CAMERA = 500;
    public static int GALLERY = 700;
    public static int CANCELLED = 4556;

    public static NewNoteFragment newInstance() {
        return new NewNoteFragment();
    }

    @Override
    public int getBindingVariable() {
        return BR.presenter;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_new_note;
    }

    @Override
    public MainActivityPresenter getPresenter() {
        return mMainPresenter;
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mFragmentNewNoteBinding = getViewDataBinding();
        setUp();
    }

    private void setUp() {
        mFragmentNewNoteBinding.fabCamera.setOnClickListener(view -> selectPictureDialog());
        mFragmentNewNoteBinding.ivWeather.setOnClickListener(view -> selectWeatherDialog());
        mFragmentNewNoteBinding.btnSave.setOnClickListener(view -> mMainPresenter.saveNote(
                new Note(
                        mFragmentNewNoteBinding.etTitle.getText().toString(),
                        mFragmentNewNoteBinding.etBody.getText().toString(),
                        mMainPresenter.getSelectedDateTimeStamp(),
                        mMainPresenter.selectedWeather.get().name(),
                        CommonUtils.bitMapToBase64(image)
                )
        ));

        mFragmentNewNoteBinding.btnBack.setOnClickListener(v ->  getBaseActivity().onFragmentDetached(TAG));
    }

    @Override
    public void handleError(String error) {
        Toast.makeText(getBaseActivity(),error,Toast.LENGTH_LONG).show();
    }

    @Override
    public void handleSuccess(String message) {
        Toast.makeText(getBaseActivity(),message,Toast.LENGTH_LONG).show();
        newFragment(NotesFragment.newInstance(),NotesFragment.TAG);
    }

    @Override
    public void onNoteSelected(NoteItemPresenter note) {

    }

    private void selectPictureDialog() {
        pictureBottomSheetFragment = new PictureBottomSheetFragment();
        pictureBottomSheetFragment.addPictureModeListener(this);
        pictureBottomSheetFragment.show(getBaseActivity().getSupportFragmentManager(), pictureBottomSheetFragment.getTag());
    }

    private void selectWeatherDialog() {
        weatherBottomSheetFragment = new WeatherBottomSheetFragment();
        weatherBottomSheetFragment.addWeatherListener(this);
        weatherBottomSheetFragment.show(getBaseActivity().getSupportFragmentManager(), weatherBottomSheetFragment.getTag());
    }

    @Override
    public void onWeatherSelected(Weather weather) {
        mMainPresenter.selectedWeather.set(weather);
        weatherBottomSheetFragment.dismiss();
    }

    @Override
    public void onPictureModeSelected(PictureMode pictureMode) {
        switch (pictureMode){
            case CAMERA:
                startActivityForResult(new Intent(MediaStore.ACTION_IMAGE_CAPTURE),CAMERA);
                break;
            case GALLERY:
                startActivityForResult(new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI),GALLERY);
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == CANCELLED) {
            return;
        }
        if (requestCode == GALLERY) {
            if (data != null) {
                try {
                    Uri contentURI = data.getData();
                    image = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), contentURI);
                    mFragmentNewNoteBinding.image.setImageBitmap(image);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

        } else if (requestCode == CAMERA) {

            Bundle bundle = data != null ?data.getExtras(): null;
            if(bundle != null && bundle.containsKey("data")) {
                image = (Bitmap) bundle.get("data");
                mFragmentNewNoteBinding.image.setImageBitmap(image);
            }
        }
    }

    public static class PictureBottomSheetFragment extends BottomSheetDialogFragment {
        PictureModeSelector listener;
        public PictureBottomSheetFragment() {
            // Required empty public constructor
        }

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            // Inflate the layout for this fragment
            ListPictureSelectionBinding binding = DataBindingUtil.inflate(inflater, R.layout.list_picture_selection, container, false);

            binding.lytCamera.setOnClickListener(view -> listener.onPictureModeSelected(PictureMode.CAMERA));
            binding.lytGallery.setOnClickListener(view -> listener.onPictureModeSelected(PictureMode.GALLERY));
            return binding.getRoot();
        }

        public void addPictureModeListener(PictureModeSelector listener) {
            this.listener = listener;
        }
    }

    public static class WeatherBottomSheetFragment extends BottomSheetDialogFragment {

        WeatherSelector listener;

        public WeatherBottomSheetFragment() {
            // Required empty public constructor
        }

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            ListWeatherBinding binding = DataBindingUtil.inflate(inflater, R.layout.list_weather, container, false);
            binding.lytCloudy.setOnClickListener(view -> listener.onWeatherSelected(Weather.CLOUDY));
            binding.lytSunny.setOnClickListener(view -> listener.onWeatherSelected(Weather.SUNNY));
            binding.lytRainy.setOnClickListener(view -> listener.onWeatherSelected(Weather.RAINY));
            binding.lytSnowy.setOnClickListener(view -> listener.onWeatherSelected(Weather.SNOWY));
            binding.lytLightening.setOnClickListener(view -> listener.onWeatherSelected(Weather.LIGHTENING));
            return binding.getRoot();
        }

        public void addWeatherListener(WeatherSelector listener) {
            this.listener = listener;
        }
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
