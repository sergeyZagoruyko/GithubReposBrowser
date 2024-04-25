package com.example.githubreposbrowser.features.gitreposlist.allrepos;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.githubreposbrowser.base.BaseInteractor;
import com.example.githubreposbrowser.base.BaseViewModel;
import com.example.githubreposbrowser.data.ScreenState;
import com.example.githubreposbrowser.features.gitreposlist.allrepos.domain.GithubRepo;
import com.example.githubreposbrowser.utils.SingleLiveEvent;

abstract public class BaseRepoViewModel extends BaseViewModel {

    @NonNull
    protected final MutableLiveData<ScreenState> _screenState = new MutableLiveData<>();
    @NonNull
    public final LiveData<ScreenState> screenState = _screenState;

    @NonNull
    public final SingleLiveEvent<Long> showRepoDetailsDialog = new SingleLiveEvent();

    public BaseRepoViewModel(@NonNull BaseInteractor baseInteractor) {
        super(baseInteractor);
    }

    public void onItemSelected(@NonNull final GithubRepo item) {
        showRepoDetailsDialog.setValue(item.id());
    }
}
