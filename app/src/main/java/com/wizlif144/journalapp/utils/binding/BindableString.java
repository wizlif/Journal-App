package com.wizlif144.journalapp.utils.binding;

import android.databinding.BaseObservable;

public class BindableString extends BaseObservable {

    String value;

    public String get() {
        return value != null ? value : "";
    }

    public void set(String value) {
        if (!Objects.equals(this.value, value)) {
            this.value = value;
            notifyChange();
        }
    }

    public boolean isEmpty() {
        return value == null || value.isEmpty();
    }
}
