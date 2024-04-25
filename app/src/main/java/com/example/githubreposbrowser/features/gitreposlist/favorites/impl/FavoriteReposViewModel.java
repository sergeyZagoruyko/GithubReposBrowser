package com.example.githubreposbrowser.features.gitreposlist.favorites.impl;

import androidx.annotation.NonNull;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleEventObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.githubreposbrowser.data.ScreenState;
import com.example.githubreposbrowser.features.gitreposlist.allrepos.BaseRepoViewModel;
import com.example.githubreposbrowser.features.gitreposlist.allrepos.domain.GithubRepo;
import com.example.githubreposbrowser.features.gitreposlist.details.domain.GithubRepoDetails;
import com.example.githubreposbrowser.features.gitreposlist.details.ui.DetailsDialogData;
import com.example.githubreposbrowser.features.gitreposlist.favorites.domain.FavoritesInteractor;
import com.example.githubreposbrowser.utils.SingleLiveEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class FavoriteReposViewModel extends BaseRepoViewModel implements LifecycleEventObserver {

    @NonNull
    public final SingleLiveEvent<DetailsDialogData> favoriteStateChanged = new SingleLiveEvent();

    @NonNull
    private final MutableLiveData<List<Long>> _favoriteIds = new MutableLiveData<>();
    @NonNull
    public final LiveData<List<Long>> favoriteIds = _favoriteIds;

    @NonNull
    private final FavoritesInteractor interactor;

    @NonNull
    private final CompositeDisposable searchReposComposable = new CompositeDisposable();

    @Inject
    public FavoriteReposViewModel(@NonNull FavoritesInteractor interactor) {
        super(interactor);
        this.interactor = interactor;
    }

    @Override
    public void onStateChanged(@NonNull LifecycleOwner lifecycleOwner, @NonNull Lifecycle.Event event) {
        if (event == Lifecycle.Event.ON_CREATE) {
            fetchFavoritesRepos(false);
        }
    }

    @Override
    public void onRefreshRepos() {
        super.onRefreshRepos();
        fetchFavoritesRepos(true);
    }

    public void onFavoriteClicked(@NonNull final GithubRepoDetails details, final boolean currentlyFavorite) {
        if (currentlyFavorite) {
            deleteFromFavorites(details.basicInfo().id());
        } else {
            addToFavorites(details.basicInfo());
        }
    }

    @Override
    protected boolean isItemFavorite(long id) {
        // Always true, since this is Favorites tab
        return true;
    }

    private void fetchFavoritesRepos(final boolean refreshing) {
        _screenState.setValue(ScreenState.loading(true, refreshing));
        searchReposComposable.clear();
        searchReposComposable.add(interactor.getGithubRepos()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onReposReceived, this::onFailedReposReceive));
    }

    private void onReposReceived(@NonNull final List<GithubRepo> list) {
        _favoriteIds.setValue(list.stream().map(GithubRepo::id).collect(Collectors.toList()));
        githubRepos = list;

        if (!githubRepos.isEmpty()) {
            _screenState.setValue(ScreenState.success(githubRepos));
        } else {
            _screenState.setValue(ScreenState.empty());
        }
    }

    private void onFailedReposReceive(final Throwable error) {
        final String errorContent = error.getMessage() != null ? error.getMessage() : getBaseErrorDataLoadingText();
        _screenState.setValue(ScreenState.error(errorContent));
    }

    private void addToFavorites(@NonNull final GithubRepo item) {
        searchReposComposable.add(interactor.addToFavorites(item)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe((favoriteId) -> onAddedToFavorites(item),
                        throwable -> errorToast.setValue(getBaseErrorText())));
    }

    private void onAddedToFavorites(@NonNull final GithubRepo item) {
        final List<GithubRepo> updatedList = new ArrayList<>();
        updatedList.add(item);
        updatedList.addAll(githubRepos);
        githubRepos = updatedList;
        favoriteStateChanged.setValue(new DetailsDialogData(item.id(), true));
        _screenState.setValue(ScreenState.success(githubRepos));

        final List<Long> updatedFavoriteIds = _favoriteIds.getValue() == null
                ? new ArrayList<>() : new ArrayList<>(_favoriteIds.getValue());
        updatedFavoriteIds.add(item.id());
        _favoriteIds.setValue(updatedFavoriteIds);
    }

    private void deleteFromFavorites(final long repoId) {
        searchReposComposable.add(interactor.deleteFromFavorites(repoId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> onDeletedFromFavorites(repoId),
                        throwable -> errorToast.setValue(getBaseErrorText())));
    }

    private void onDeletedFromFavorites(final long repoId) {
        favoriteStateChanged.setValue(new DetailsDialogData(repoId, false));
        _screenState.setValue(ScreenState.success(new ArrayList<>(githubRepos
                .stream().filter(githubRepo -> githubRepo.id() != repoId).collect(Collectors.toList()))));

        final List<Long> updatedFavoriteIds = _favoriteIds.getValue() == null
                ? new ArrayList<>() : new ArrayList<>(_favoriteIds.getValue());
        _favoriteIds.setValue(updatedFavoriteIds.stream()
                .filter(id -> id != repoId).collect(Collectors.toList()));

    }

    @Override
    protected void onCleared() {
        searchReposComposable.clear();
        super.onCleared();
    }
}