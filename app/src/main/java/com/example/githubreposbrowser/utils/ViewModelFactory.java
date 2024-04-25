package com.example.githubreposbrowser.utils;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.githubreposbrowser.base.BaseViewModel;

import javax.inject.Inject;
import javax.inject.Provider;

public class ViewModelFactory<VM extends BaseViewModel> implements ViewModelProvider.Factory {

    @NonNull
    private final Provider<VM> vmProvider;

    @Inject
    public ViewModelFactory(@NonNull Provider<VM> vmProvider) {
        this.vmProvider = vmProvider;
    }

    @SuppressWarnings("unchecked")
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        try {
            return (T) vmProvider.get();
        } catch (Exception e) {
            throw new RuntimeException("Error creating view model", e);
        }
    }
}
