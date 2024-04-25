package com.example.githubreposbrowser.features.gitreposlist.allrepos.ui;

import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.githubreposbrowser.di.component.AppComponent;
import com.example.githubreposbrowser.features.gitreposlist.BaseRepoListFragment;
import com.example.githubreposbrowser.features.gitreposlist.allrepos.BaseRepoViewModel;
import com.example.githubreposbrowser.features.gitreposlist.allrepos.di.RepoListFrmComponent;
import com.example.githubreposbrowser.features.gitreposlist.allrepos.impl.ReposListViewModel;
import com.example.githubreposbrowser.features.gitreposlist.favorites.impl.FavoriteReposViewModel;
import com.example.githubreposbrowser.listeners.OnItemSelectedListener;
import com.example.githubreposbrowser.utils.PagingScrollChangeListener;

public class ReposListFragment extends BaseRepoListFragment {

    private ReposListViewModel viewModel;
    private FavoriteReposViewModel sharedFavoritesViewModel;

    @NonNull
    public static ReposListFragment newInstance() {
        return new ReposListFragment();
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
            searchBarHolder.setSearchItemsVisibility(true);
        }
    }

    @Override
    public void setupDI(AppComponent appComponent) {
        RepoListFrmComponent component = appComponent.plusRepoListFrm().create();
        component.inject(this);
        viewModel = new ViewModelProvider(this, component.vm()).get(ReposListViewModel.class);
        sharedFavoritesViewModel = new ViewModelProvider(requireActivity(), component.favoritesSharedVM()).get(FavoriteReposViewModel.class);
    }

    @NonNull
    @Override
    protected BaseRepoViewModel getViewModel() {
        return viewModel;
    }

    @Override
    protected void setupObservers() {
        super.setupObservers();
        observeNonNull(viewModel.errorToast, text -> Toast.makeText(requireContext(), text, Toast.LENGTH_LONG).show());
        observeNonNull(sharedFavoritesViewModel.favoriteIds, ids -> viewModel.onFavoriteIdsChanged(ids));
    }

    @Override
    protected void setupListeners() {
        super.setupListeners();
        if (searchBarHolder != null) {
            searchBarHolder.setOnTextChangeListener(text -> viewModel.onSearchTextEntered(text));
            searchBarHolder.setOnFilterClickedListener(viewModel::onFilterItemSelected);
            searchBarHolder.setOnFilterClickedListener((OnItemSelectedListener<GitReposFilterType>) item ->
                    viewModel.onFilterItemSelected(item));
        }
        if (binding.rvGithubRepos.getLayoutManager() != null) {
            binding.rvGithubRepos.setOnScrollChangeListener(
                    new PagingScrollChangeListener((LinearLayoutManager) binding.rvGithubRepos.getLayoutManager(),
                            () -> viewModel.onScrolledToNext()));
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding.rvGithubRepos.setAdapter(null);
        binding.rvGithubRepos.setOnScrollChangeListener(null);
    }
}
