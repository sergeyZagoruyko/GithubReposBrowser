package com.example.githubreposbrowser.features.gitrepostabs;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.githubreposbrowser.base.BaseFragment;
import com.example.githubreposbrowser.databinding.FragmentGitReposTabsBinding;
import com.google.android.material.tabs.TabLayoutMediator;

public class GitReposTabsFragment extends BaseFragment {

    private FragmentGitReposTabsBinding binding;
    private GitReposPagerAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        binding = FragmentGitReposTabsBinding.inflate(inflater, container, false);

        initTabs();
        return binding.getRoot();
    }

    private void initTabs() {
        adapter = new GitReposPagerAdapter(getParentFragmentManager(), getLifecycle());
        binding.vpGitRepos.setAdapter(adapter);

        new TabLayoutMediator(binding.tabLayout, binding.vpGitRepos, false, true,
                (tab, position) -> tab.setText(adapter.getTabTypeByPos(position).getTabNameRes())).attach();
    }
}