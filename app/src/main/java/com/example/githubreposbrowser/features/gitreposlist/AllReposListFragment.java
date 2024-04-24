package com.example.githubreposbrowser.features.gitreposlist;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.githubreposbrowser.R;
import com.example.githubreposbrowser.base.BaseFragment;
import com.example.githubreposbrowser.databinding.FragmentGitReposListBinding;

public class AllReposListFragment extends BaseFragment {

    private FragmentGitReposListBinding binding;

    @NonNull
    public static AllReposListFragment newInstance() {
        return new AllReposListFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        binding = FragmentGitReposListBinding.inflate(inflater, container, false);
        initUI();
        return binding.getRoot();
    }

    private void initUI() {
        binding.tvText.setText(getString(R.string.all_repos_tab_title));
    }
}