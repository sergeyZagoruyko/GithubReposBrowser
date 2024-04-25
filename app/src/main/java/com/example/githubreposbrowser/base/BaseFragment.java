package com.example.githubreposbrowser.base;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

import com.example.githubreposbrowser.di.component.AppComponent;
import com.example.githubreposbrowser.utils.LiveDataUtils;

abstract public class BaseFragment extends Fragment {

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        setupDI(App.getComponent());
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupObservers();
        setupListeners();
    }

    protected abstract void setupDI(AppComponent appComponent);

    protected void setupObservers() {
    }

    protected void setupListeners() {
    }

    protected <T> void observeNonNull(@NonNull final LiveData<T> liveData, @NonNull final Observer<T> observer) {
        LiveDataUtils.observeNonNull(liveData, getViewLifecycleOwner(), observer);
    }
}
