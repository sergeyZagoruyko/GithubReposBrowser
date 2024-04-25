package com.example.githubreposbrowser.features.gitreposlist.allrepos.di;

import com.example.githubreposbrowser.di.scope.PerFragment;
import com.example.githubreposbrowser.features.gitreposlist.allrepos.impl.ReposListViewModel;
import com.example.githubreposbrowser.features.gitreposlist.allrepos.ui.ReposListFragment;
import com.example.githubreposbrowser.features.gitreposlist.favorites.impl.FavoriteReposViewModel;
import com.example.githubreposbrowser.utils.ViewModelFactory;

import dagger.Subcomponent;

@PerFragment
@Subcomponent
public interface RepoListFrmComponent {

    @Subcomponent.Factory
    interface Factory {
        RepoListFrmComponent create();
    }

    ViewModelFactory<ReposListViewModel> vm();

    ViewModelFactory<FavoriteReposViewModel> favoritesSharedVM();

    void inject(final ReposListFragment fragment);
}
