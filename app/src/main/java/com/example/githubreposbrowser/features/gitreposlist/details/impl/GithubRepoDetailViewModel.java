package com.example.githubreposbrowser.features.gitreposlist.details.impl;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.githubreposbrowser.base.BaseViewModel;
import com.example.githubreposbrowser.data.ScreenState;
import com.example.githubreposbrowser.features.gitreposlist.details.domain.GithubRepoDetails;
import com.example.githubreposbrowser.features.gitreposlist.details.domain.GithubRepoDetailsInteractor;

import javax.inject.Inject;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;


public class GithubRepoDetailViewModel extends BaseViewModel {

    @NonNull
    private final MutableLiveData<ScreenState> _screenState = new MutableLiveData<>();
    @NonNull
    public final LiveData<ScreenState> screenState = _screenState;

    @NonNull
    private final GithubRepoDetailsInteractor interactor;
    @NonNull
    private final CompositeDisposable detailsDisposable = new CompositeDisposable();

    @Inject
    public GithubRepoDetailViewModel(GithubRepoDetailsInteractor interactor) {
        super(interactor);
        this.interactor = interactor;
    }

    public void onViewCreated(final long githubRepoId) {
        if (githubRepoId <= 0) {
            _screenState.setValue(ScreenState.error(getBaseErrorDataLoadingText()));
            return;
        }

        _screenState.setValue(ScreenState.loading(true, false));
        detailsDisposable.clear();
        detailsDisposable.add(interactor.getRepoDetails(githubRepoId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onDetailsReceived, this::onFailedDetailsReceive));
    }

    private void onDetailsReceived(@NonNull final GithubRepoDetails details) {
        _screenState.setValue(ScreenState.success(details));
    }

    private void onFailedDetailsReceive(final Throwable error) {
        final String errorContent = error.getMessage() != null ? error.getMessage() : getBaseErrorDataLoadingText();
        _screenState.setValue(ScreenState.error(errorContent));
    }

    @Override
    protected void onCleared() {
        detailsDisposable.clear();
        super.onCleared();
    }
}
