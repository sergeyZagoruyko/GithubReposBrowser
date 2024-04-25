package com.example.githubreposbrowser.base;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

import com.example.githubreposbrowser.di.component.AppComponent;
import com.example.githubreposbrowser.utils.LiveDataUtils;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

abstract public class BaseBottomSheetDialog extends BottomSheetDialogFragment {

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        setupDI(App.getComponent());
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupObservers();
    }

    protected void setupDI(AppComponent appComponent) {
    }

    protected void setupObservers() {
    }

    protected <T> void observeNonNull(@NonNull final LiveData<T> liveData, @NonNull final Observer<T> observer) {
        LiveDataUtils.observeNonNull(liveData, getViewLifecycleOwner(), observer);
    }
}
