package com.example.githubreposbrowser.features.gitreposlist.allrepos.impl;

import androidx.annotation.NonNull;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleEventObserver;
import androidx.lifecycle.LifecycleOwner;

import com.example.githubreposbrowser.base.BaseViewModel;

import javax.inject.Inject;

public class ReposListViewModel extends BaseViewModel implements LifecycleEventObserver {

    @NonNull
    private final GithubReposInteractor interactor;

    @Inject
    public ReposListViewModel(@NonNull final GithubReposInteractor interactor) {
        this.interactor = interactor;
    }

    @Override
    public void onStateChanged(@NonNull LifecycleOwner lifecycleOwner, @NonNull Lifecycle.Event event) {
        if (event == Lifecycle.Event.ON_CREATE) {
            onCreated();
        }
    }

    private void onCreated() {

    }
}
