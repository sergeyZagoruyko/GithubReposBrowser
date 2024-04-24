package com.example.githubreposbrowser.features.gitreposlist.allrepos.ui;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.githubreposbrowser.base.BaseFragment;
import com.example.githubreposbrowser.databinding.FragmentGitReposListBinding;
import com.example.githubreposbrowser.di.component.AppComponent;
import com.example.githubreposbrowser.features.SearchBarHolder;
import com.example.githubreposbrowser.features.gitreposlist.allrepos.di.RepoListFrmComponent;
import com.example.githubreposbrowser.features.gitreposlist.allrepos.impl.ReposListViewModel;

public class ReposListFragment extends BaseFragment {

    private FragmentGitReposListBinding binding;
    private ReposListViewModel viewModel;

    @NonNull
    private final RepoListAdapter adapter = new RepoListAdapter();

    @Nullable
    private SearchBarHolder searchBarHolder = null;

    @NonNull
    public static ReposListFragment newInstance() {
        return new ReposListFragment();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        getLifecycle().addObserver(viewModel);
        searchBarHolder = getSearchBarHolderImpl();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (searchBarHolder != null) {
            searchBarHolder.setOnTextChangeListener(text -> viewModel.onSearchTextEntered(text));
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        binding = FragmentGitReposListBinding.inflate(inflater, container, false);
        initUI();
        return binding.getRoot();
    }

    @Override
    public void setupDI(AppComponent appComponent) {
        RepoListFrmComponent component = appComponent.plusRepoListFrm().create();
        component.inject(this);
        viewModel = new ViewModelProvider(this, component.vm()).get(ReposListViewModel.class);
    }

    @Override
    protected void setupObservers() {
        super.setupObservers();
        observeNonNull(viewModel.githubRepos, adapter::submitList);
    }

    @Override
    protected void setupListeners() {
        super.setupListeners();
        if (searchBarHolder != null) {
            searchBarHolder.setOnTextChangeListener(text -> viewModel.onSearchTextEntered(text));
        }
    }

    private void initUI() {
        binding.rvGithubRepos.setAdapter(adapter);
    }

    @Nullable
    private SearchBarHolder getSearchBarHolderImpl() {
        for (Fragment frm : getParentFragmentManager().getFragments()) {
            if (frm instanceof SearchBarHolder) {
                return (SearchBarHolder) frm;
            }
        }
        return null;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (searchBarHolder != null) {
            searchBarHolder.setOnTextChangeListener(null);
        }
        binding.rvGithubRepos.setAdapter(null);
    }
}
