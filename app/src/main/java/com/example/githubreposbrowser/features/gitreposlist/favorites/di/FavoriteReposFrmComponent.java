package com.example.githubreposbrowser.features.gitreposlist.favorites.di;

import com.example.githubreposbrowser.di.scope.PerFragment;
import com.example.githubreposbrowser.features.gitreposlist.favorites.ui.FavoriteReposFragment;
import com.example.githubreposbrowser.features.gitreposlist.favorites.impl.FavoriteReposViewModel;
import com.example.githubreposbrowser.utils.ViewModelFactory;

import dagger.Subcomponent;

@PerFragment
@Subcomponent
public interface FavoriteReposFrmComponent {

    @Subcomponent.Factory
    interface Factory {
        FavoriteReposFrmComponent create();
    }

    ViewModelFactory<FavoriteReposViewModel> vm();

    void inject(final FavoriteReposFragment fragment);
}
