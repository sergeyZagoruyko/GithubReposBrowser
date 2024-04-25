package com.example.githubreposbrowser.features.gitreposlist.favorites;

import androidx.annotation.NonNull;

import com.example.githubreposbrowser.features.gitreposlist.allrepos.BaseRepoViewModel;

import javax.inject.Inject;

public class FavoriteReposViewModel extends BaseRepoViewModel {

    @NonNull
    private final FavoritesInteractor interactor;

    @Inject
    public FavoriteReposViewModel(@NonNull FavoritesInteractor interactor) {
        super(interactor);
        this.interactor = interactor;
    }


}