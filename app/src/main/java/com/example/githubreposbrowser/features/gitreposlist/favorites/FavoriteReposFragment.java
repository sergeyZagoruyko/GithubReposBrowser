package com.example.githubreposbrowser.features.gitreposlist.favorites;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;

import com.example.githubreposbrowser.di.component.AppComponent;
import com.example.githubreposbrowser.features.gitreposlist.BaseRepoListFragment;
import com.example.githubreposbrowser.features.gitreposlist.allrepos.BaseRepoViewModel;

public class FavoriteReposFragment extends BaseRepoListFragment {

    private FavoriteReposViewModel viewModel;

    @Override
    public void setupDI(AppComponent appComponent) {
        FavoriteReposFrmComponent component = appComponent.plusFavoriteReposFrm().create();
        component.inject(this);
        viewModel = new ViewModelProvider(requireActivity(), component.vm()).get(FavoriteReposViewModel.class);
    }

    @NonNull
    @Override
    protected BaseRepoViewModel getViewModel() {
        return viewModel;
    }

    @Override
    protected void initUI() {
        super.initUI();
        if (searchBarHolder != null) {
            searchBarHolder.setSearchItemsVisibility(false);
        }
    }
}
