package com.example.githubreposbrowser.features.gitreposlist.allrepos.impl;

import androidx.annotation.NonNull;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleEventObserver;
import androidx.lifecycle.LifecycleOwner;

import com.example.githubreposbrowser.base.BaseViewModel;
import com.example.githubreposbrowser.features.gitreposlist.allrepos.domain.GithubReposInteractor;

import javax.inject.Inject;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class ReposListViewModel extends BaseViewModel implements LifecycleEventObserver {

    @NonNull
    private final GithubReposInteractor interactor;

    @NonNull
    private final CompositeDisposable searchReposComposable = new CompositeDisposable();

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
        searchGitRepos();
    }

    private void searchGitRepos() {
        searchReposComposable.clear();
        searchReposComposable.add(interactor.searchGithubRepos()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(result -> {
                        },
                        error -> {
                        }));
    }

    @Override
    protected void onCleared() {
        searchReposComposable.clear();
        super.onCleared();
    }
}
