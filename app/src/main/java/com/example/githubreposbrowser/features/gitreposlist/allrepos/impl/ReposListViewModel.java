package com.example.githubreposbrowser.features.gitreposlist.allrepos.impl;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleEventObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.githubreposbrowser.base.BaseViewModel;
import com.example.githubreposbrowser.data.ScreenState;
import com.example.githubreposbrowser.features.gitreposlist.allrepos.domain.GithubRepo;
import com.example.githubreposbrowser.features.gitreposlist.allrepos.domain.GithubReposInteractor;
import com.example.githubreposbrowser.features.gitreposlist.allrepos.ui.GitReposFilterType;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class ReposListViewModel extends BaseViewModel implements LifecycleEventObserver {

    @NonNull
    private final MutableLiveData<ScreenState> _screenState = new MutableLiveData<>();
    @NonNull
    public final LiveData<ScreenState> screenState = _screenState;

    @NonNull
    private final GithubReposInteractor interactor;

    @NonNull
    private final CompositeDisposable searchReposComposable = new CompositeDisposable();

    @NonNull
    private GitReposFilterType currentFilterType = GitReposFilterType.getDefaultValue();
    @Nullable
    private String searchQuery = null;

    @Inject
    public ReposListViewModel(@NonNull final GithubReposInteractor interactor) {
        this.interactor = interactor;
    }

    @Override
    public void onStateChanged(@NonNull LifecycleOwner lifecycleOwner, @NonNull Lifecycle.Event event) {
        if (event == Lifecycle.Event.ON_CREATE) {
            searchGitRepos(null, currentFilterType, false);
        }
    }

    public void onRefreshRepos() {
        searchGitRepos(searchQuery, currentFilterType, true);
    }

    public void onSearchTextEntered(@Nullable final String text) {
        searchQuery = text;
        searchGitRepos(text, currentFilterType, false);
    }

    public void onFilterItemSelected(final GitReposFilterType selectedFilterType) {
        currentFilterType = selectedFilterType;
        searchGitRepos(searchQuery, currentFilterType, false);
    }

    private void searchGitRepos(@Nullable final String searchQuery, @NonNull final GitReposFilterType filterType, final boolean refreshing) {
        _screenState.setValue(ScreenState.loading(true, refreshing));
        searchReposComposable.clear();
        searchReposComposable.add(interactor.searchGithubRepos(searchQuery, filterType)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onReposReceived, this::onFailedReposReceive));
    }

    private void onReposReceived(List<GithubRepo> list) {
        _screenState.setValue(ScreenState.success(list));
    }

    private void onFailedReposReceive(final Throwable error) {
        final String errorContent = error.getMessage() != null ? error.getMessage() : "";
        _screenState.setValue(ScreenState.error(errorContent));
    }

    @Override
    protected void onCleared() {
        searchReposComposable.clear();
        super.onCleared();
    }
}
