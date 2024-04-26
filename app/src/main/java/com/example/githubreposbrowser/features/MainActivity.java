package com.example.githubreposbrowser.features;

import static com.example.githubreposbrowser.utils.ViewUtils.setVisibleOrGone;

import android.app.assist.AssistContent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;

import com.example.githubreposbrowser.R;
import com.example.githubreposbrowser.base.App;
import com.example.githubreposbrowser.base.BaseActivity;
import com.example.githubreposbrowser.databinding.ActivityMainBinding;
import com.example.githubreposbrowser.features.gitrepostabs.GitReposTabsFragment;

public class MainActivity extends BaseActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.frmGitReposTabs, new GitReposTabsFragment())
                    .commit();
        }

        initUI();
    }

    @Override
    protected void onResume() {
        super.onResume();
        App.getInstance().setExpiredTokenListener(expired ->
                binding.tvBanner.post(() -> setVisibleOrGone(binding.tvBanner, expired)));
    }

    @Override
    protected void onPause() {
        super.onPause();
        App.getInstance().setExpiredTokenListener(null);
    }

    private void initUI() {
        binding.tvBanner.setOnClickListener(v -> v.setVisibility(View.GONE));
    }
}
