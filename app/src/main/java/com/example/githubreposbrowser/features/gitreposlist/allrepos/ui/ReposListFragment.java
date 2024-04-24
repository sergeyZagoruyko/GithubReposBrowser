package com.example.githubreposbrowser.features.gitreposlist.allrepos.ui;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.githubreposbrowser.R;
import com.example.githubreposbrowser.base.BaseFragment;
import com.example.githubreposbrowser.databinding.FragmentGitReposListBinding;
import com.example.githubreposbrowser.di.component.AppComponent;
import com.example.githubreposbrowser.features.gitreposlist.allrepos.di.RepoListFrmComponent;
import com.example.githubreposbrowser.features.gitreposlist.allrepos.domain.GithubRepo;
import com.example.githubreposbrowser.features.gitreposlist.allrepos.impl.ReposListViewModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ReposListFragment extends BaseFragment {

    private FragmentGitReposListBinding binding;
    private ReposListViewModel viewModel;

    @NonNull
    private final RepoListAdapter adapter = new RepoListAdapter();

    @NonNull
    public static ReposListFragment newInstance() {
        return new ReposListFragment();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        getLifecycle().addObserver(viewModel);
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

    private void initUI() {
        binding.rvGithubRepos.setAdapter(adapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding.rvGithubRepos.setAdapter(null);
    }
}
