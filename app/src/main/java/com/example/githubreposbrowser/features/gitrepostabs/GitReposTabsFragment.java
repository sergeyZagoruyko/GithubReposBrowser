package com.example.githubreposbrowser.features.gitrepostabs;

import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.githubreposbrowser.R;
import com.example.githubreposbrowser.base.BaseFragment;
import com.example.githubreposbrowser.databinding.FragmentGitReposTabsBinding;
import com.example.githubreposbrowser.di.component.AppComponent;
import com.example.githubreposbrowser.features.SearchBarHolder;
import com.example.githubreposbrowser.listeners.OnTextChange;
import com.example.githubreposbrowser.utils.ViewUtils;
import com.google.android.material.tabs.TabLayoutMediator;

import java.lang.ref.WeakReference;

import io.reactivex.rxjava3.disposables.CompositeDisposable;

public class GitReposTabsFragment extends BaseFragment implements SearchBarHolder {

    private FragmentGitReposTabsBinding binding;
    private GitReposPagerAdapter adapter;

    @Nullable
    private WeakReference<OnTextChange> onSearchTextChanged = null;

    @NonNull
    private final CompositeDisposable searchInputDisposable = new CompositeDisposable();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        binding = FragmentGitReposTabsBinding.inflate(inflater, container, false);

        ((AppCompatActivity) getActivity()).setSupportActionBar(binding.toolbar);
        setupSearchView();

        initTabs();
        return binding.getRoot();
    }

    @Override
    public void setupDI(AppComponent appComponent) {

    }


    @Override
    public void setOnTextChangeListener(OnTextChange listener) {
        onSearchTextChanged = new WeakReference<>(listener);
    }

    private void initTabs() {
        adapter = new GitReposPagerAdapter(getParentFragmentManager(), getLifecycle());
        binding.vpGitRepos.setAdapter(adapter);

        new TabLayoutMediator(binding.tabLayout, binding.vpGitRepos, false, true,
                (tab, position) -> tab.setText(adapter.getTabTypeByPos(position).getTabNameRes())).attach();
    }

    private void setupSearchView() {
        // Change default colors to specified ones. Not accessible via xml
        final int iconsColor = ContextCompat.getColor(requireContext(), R.color.colorAccent);

        ((TextView) binding.searchView.findViewById(androidx.appcompat.R.id.search_src_text))
                .setTextColor(ContextCompat.getColor(requireContext(), R.color.colorAccent));

        ((ImageView) binding.searchView.findViewById(androidx.appcompat.R.id.search_button))
                .setColorFilter(iconsColor, PorterDuff.Mode.SRC_IN);

        ((ImageView) binding.searchView.findViewById(androidx.appcompat.R.id.search_close_btn))
                .setColorFilter(iconsColor, PorterDuff.Mode.SRC_IN);

        searchInputDisposable.add(ViewUtils.initAsyncTextChangeHandling(binding.searchView, this::onSearchTextEntered));
    }

    private void onSearchTextEntered(final String text) {
        if (onSearchTextChanged != null && onSearchTextChanged.get() != null) {
            onSearchTextChanged.get().onTextChanged(text);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        searchInputDisposable.clear();
        binding.vpGitRepos.setAdapter(null);
    }
}