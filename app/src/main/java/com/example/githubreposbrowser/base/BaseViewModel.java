package com.example.githubreposbrowser.base;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;

import com.example.githubreposbrowser.R;

abstract public class BaseViewModel extends ViewModel {

    @NonNull
    private final BaseInteractor baseInteractor;

    public BaseViewModel(@NonNull BaseInteractor baseInteractor) {
        this.baseInteractor = baseInteractor;
    }

    protected String getBasicErrorText() {
        return baseInteractor.getString(R.string.base_error_text);
    }
}
