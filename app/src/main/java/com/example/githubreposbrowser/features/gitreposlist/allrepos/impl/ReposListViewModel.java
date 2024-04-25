package com.example.githubreposbrowser.features.gitreposlist.allrepos.impl;

import static com.example.githubreposbrowser.utils.Constants.API_ERROR_STATUS_CODE_AUTH_FAILED;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleEventObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.githubreposbrowser.R;
import com.example.githubreposbrowser.data.ScreenState;
import com.example.githubreposbrowser.features.gitreposlist.allrepos.BaseRepoViewModel;
import com.example.githubreposbrowser.features.gitreposlist.allrepos.domain.GithubRepo;
import com.example.githubreposbrowser.features.gitreposlist.allrepos.domain.GithubRepoListData;
import com.example.githubreposbrowser.features.gitreposlist.allrepos.domain.GithubReposInteractor;
import com.example.githubreposbrowser.features.gitreposlist.allrepos.ui.GitReposFilterType;
import com.example.githubreposbrowser.utils.SingleLiveEvent;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import retrofit2.HttpException;

public class ReposListViewModel extends BaseRepoViewModel implements LifecycleEventObserver {

    private static final int DEF_CURRENT_PAGE = 1;

    @NonNull
    public final SingleLiveEvent<String> errorToast = new SingleLiveEvent();

    @NonNull
    private final GithubReposInteractor interactor;
    @NonNull
    private final CompositeDisposable searchReposComposable = new CompositeDisposable();

    @NonNull
    private GitReposFilterType currentFilterType = GitReposFilterType.getDefaultValue();
    @Nullable
    private String searchQuery = null;
    private int currentPage = DEF_CURRENT_PAGE;
    private int totalAvailableItemsCount;

    @NonNull
    private List<GithubRepo> githubRepos = new ArrayList<>();
    @NonNull
    private final GithubRepo loaderItem = GithubRepo.newInstanceLoader();

    @Inject
    public ReposListViewModel(@NonNull final GithubReposInteractor interactor) {
        super(interactor);
        this.interactor = interactor;
    }

    @Override
    public void onStateChanged(@NonNull LifecycleOwner lifecycleOwner, @NonNull Lifecycle.Event event) {
        if (event == Lifecycle.Event.ON_CREATE) {
            searchGitRepos(null, currentFilterType, false);
        }
    }

    public void onRefreshRepos() {
        currentPage = DEF_CURRENT_PAGE;
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

    public void onScrolledToNext() {
        if (githubRepos.size() >= totalAvailableItemsCount) {
            return;
        }
        currentPage++;
        setLoaderItemVisibility(true);
        _screenState.setValue(ScreenState.success(githubRepos));
        searchGitRepos(searchQuery, currentFilterType, false);
    }

    private void searchGitRepos(@Nullable final String searchQuery, @NonNull final GitReposFilterType filterType,
                                final boolean refreshing) {
        if (currentPage <= DEF_CURRENT_PAGE) {
            _screenState.setValue(ScreenState.loading(true, refreshing));
        }
        searchReposComposable.clear();
        searchReposComposable.add(interactor.searchGithubRepos(searchQuery, filterType, currentPage)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onReposReceived, this::onFailedReposReceive));
    }

    private void onReposReceived(@NonNull final GithubRepoListData listData) {
        totalAvailableItemsCount = listData.totalCount();
        setLoaderItemVisibility(false);

        final List<GithubRepo> updatedList = new ArrayList<>(githubRepos);
        updatedList.addAll(listData.items());
        githubRepos = updatedList;

        if (!githubRepos.isEmpty()) {
            _screenState.setValue(ScreenState.success(githubRepos));
        } else {
            _screenState.setValue(ScreenState.empty());
        }
    }

    private void onFailedReposReceive(final Throwable error) {
        // In the case of token overusing skip the paging and apply empty list instead of the next part
        if (error instanceof HttpException && ((HttpException) error).code() == API_ERROR_STATUS_CODE_AUTH_FAILED) {
            onReposReceived(new GithubRepoListData(totalAvailableItemsCount, Collections.emptyList()));
            errorToast.setValue(interactor.getString(R.string.error_invalid_token));
            return;
        }

        final String errorContent = error.getMessage() != null ? error.getMessage() : getBasicErrorText();
        if (currentPage > DEF_CURRENT_PAGE) {
            errorToast.setValue(errorContent);
            return;
        }
        _screenState.setValue(ScreenState.error(errorContent));
    }

    private void setLoaderItemVisibility(final boolean visible) {
        if (visible) {
            if (!githubRepos.contains(loaderItem)) {
                githubRepos.add(GithubRepo.newInstanceLoader());
            }
        } else {
            githubRepos.remove(loaderItem);
        }
    }

    @Override
    protected void onCleared() {
        searchReposComposable.clear();
        super.onCleared();
    }
}
