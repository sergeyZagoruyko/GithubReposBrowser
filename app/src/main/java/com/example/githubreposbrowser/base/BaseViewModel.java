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

    protected String getBaseErrorText() {
        return baseInteractor.getString(R.string.base_error_text);
    }

    protected String getBaseErrorDataLoadingText() {
        return baseInteractor.getString(R.string.base_error_data_loading_text);
    }

    protected String parseApiError(@NonNull final Throwable error) {
        return baseInteractor.parseApiError(error, getBaseErrorDataLoadingText());
    }
}
