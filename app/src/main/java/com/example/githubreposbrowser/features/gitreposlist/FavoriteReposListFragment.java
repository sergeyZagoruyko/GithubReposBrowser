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
import com.example.githubreposbrowser.di.component.AppComponent;

public class FavoriteReposListFragment extends BaseFragment {

    private FragmentGitReposListBinding binding;

    @NonNull
    public static FavoriteReposListFragment newInstance() {
        return new FavoriteReposListFragment();
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

    }

    private void initUI() {

    }
}

