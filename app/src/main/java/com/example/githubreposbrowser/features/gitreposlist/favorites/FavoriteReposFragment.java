package com.example.githubreposbrowser.features.gitreposlist.favorites;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;

import com.example.githubreposbrowser.di.component.AppComponent;
import com.example.githubreposbrowser.features.gitreposlist.BaseRepoListFragment;
import com.example.githubreposbrowser.features.gitreposlist.allrepos.BaseRepoViewModel;

public class FavoriteReposFragment extends BaseRepoListFragment {

    private FavoriteReposViewModel viewModel;

    public static FavoriteReposFragment newInstance() {
        return new FavoriteReposFragment();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        getLifecycle().addObserver(viewModel);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (searchBarHolder != null) {
            searchBarHolder.setSearchItemsVisibility(false);
        }
    }

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
}
