package com.example.githubreposbrowser.features;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.example.githubreposbrowser.R;
import com.example.githubreposbrowser.base.BaseActivity;
import com.example.githubreposbrowser.databinding.ActivityMainBinding;
import com.example.githubreposbrowser.features.gitrepostabs.GitReposTabsFragment;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.frmGitReposTabs, new GitReposTabsFragment())
                    .commit();
        }
    }
}
