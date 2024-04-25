package com.example.githubreposbrowser.features.gitreposlist.allrepos;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.githubreposbrowser.base.BaseInteractor;
import com.example.githubreposbrowser.base.BaseViewModel;
import com.example.githubreposbrowser.data.ScreenState;
import com.example.githubreposbrowser.features.gitreposlist.allrepos.domain.GithubRepo;
import com.example.githubreposbrowser.features.gitreposlist.details.ui.DetailsDialogData;
import com.example.githubreposbrowser.utils.SingleLiveEvent;

import java.util.ArrayList;
import java.util.List;

abstract public class BaseRepoViewModel extends BaseViewModel {

    @NonNull
    public final SingleLiveEvent<String> errorToast = new SingleLiveEvent();

    @NonNull
    public final SingleLiveEvent<DetailsDialogData> showRepoDetailsDialog = new SingleLiveEvent();

    @NonNull
    protected final MutableLiveData<ScreenState> _screenState = new MutableLiveData<>();
    @NonNull
    public final LiveData<ScreenState> screenState = _screenState;

    @NonNull
    protected List<GithubRepo> githubRepos = new ArrayList<>();

    protected abstract boolean isItemFavorite(final long id);

    public BaseRepoViewModel(@NonNull BaseInteractor baseInteractor) {
        super(baseInteractor);
    }

    public void onRefreshRepos() {
    }

    public void onItemSelected(@NonNull final GithubRepo item) {
        final DetailsDialogData dialogData = new DetailsDialogData(item.id(), isItemFavorite(item.id()));
        showRepoDetailsDialog.setValue(dialogData);
    }
}
