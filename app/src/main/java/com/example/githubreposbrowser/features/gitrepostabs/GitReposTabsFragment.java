package com.example.githubreposbrowser.features.gitrepostabs;

import static com.example.githubreposbrowser.utils.ViewUtils.setVisibleOrGone;

import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.githubreposbrowser.R;
import com.example.githubreposbrowser.base.BaseFragment;
import com.example.githubreposbrowser.databinding.FragmentGitReposTabsBinding;
import com.example.githubreposbrowser.di.component.AppComponent;
import com.example.githubreposbrowser.features.gitreposlist.allrepos.ui.GitReposFilterType;
import com.example.githubreposbrowser.listeners.OnItemSelectedListener;
import com.example.githubreposbrowser.listeners.OnTextChangedListener;
import com.example.githubreposbrowser.utils.ViewUtils;
import com.example.githubreposbrowser.view.SearchBarHolder;
import com.google.android.material.tabs.TabLayoutMediator;

import io.reactivex.rxjava3.disposables.CompositeDisposable;

public class GitReposTabsFragment extends BaseFragment implements SearchBarHolder {

    private FragmentGitReposTabsBinding binding;
    private GitReposPagerAdapter adapter;

    @Nullable
    private OnTextChangedListener onSearchTextChanged = null;
    @Nullable
    private OnItemSelectedListener onFilterClickedListener = null;

    @Nullable
    private PopupMenu filterPopupMenu;
    @NonNull
    private GitReposFilterType lastSelectedFilterType = GitReposFilterType.getDefaultValue();

    @NonNull
    private final CompositeDisposable searchInputDisposable = new CompositeDisposable();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        binding = FragmentGitReposTabsBinding.inflate(inflater, container, false);
        initUI();

        return binding.getRoot();
    }

    @Override
    public void setupDI(AppComponent appComponent) {
    }

    @Override
    protected void setupListeners() {
        super.setupListeners();
        binding.ivFilter.setOnClickListener(v -> {
            if (filterPopupMenu != null) {
                filterPopupMenu.show();
            }
        });
    }

    @Override
    public void setOnTextChangeListener(OnTextChangedListener listener) {
        onSearchTextChanged = listener;
    }

    public <T> void setOnFilterClickedListener(@Nullable OnItemSelectedListener<T> onFilterClickedListener) {
        this.onFilterClickedListener = onFilterClickedListener;
    }

    @Override
    public void setSearchItemsVisibility(boolean visible) {
        setVisibleOrGone(binding.searchView, visible);
        setVisibleOrGone(binding.ivFilter, visible);
    }

    private void initUI() {
        ((AppCompatActivity) requireActivity()).setSupportActionBar(binding.toolbar);

        setupFilterPopupMenu();
        setupSearchView();
        initTabs();
    }

    private void initTabs() {
        adapter = new GitReposPagerAdapter(getChildFragmentManager(), getLifecycle());
        binding.vpGitRepos.setAdapter(adapter);
        binding.vpGitRepos.setOffscreenPageLimit(GitReposPagerAdapter.PageType.values().length);

        new TabLayoutMediator(binding.tabLayout, binding.vpGitRepos, false, true,
                (tab, position) -> tab.setText(adapter.getTabTypeByPos(position).getTabNameResId())).attach();
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

    private void setupFilterPopupMenu() {
        filterPopupMenu = new PopupMenu(requireContext(), binding.ivFilter);
        filterPopupMenu.getMenuInflater().inflate(R.menu.filter_type_popup_menu, filterPopupMenu.getMenu());
        filterPopupMenu.setOnMenuItemClickListener(item -> {
            lastSelectedFilterType = GitReposFilterType.getByItemId(item.getItemId());
            if (onFilterClickedListener != null) {
                onFilterClickedListener.onItemSelected(lastSelectedFilterType);
            }
            return true;
        });

    }

    private void onSearchTextEntered(final String text) {
        if (onSearchTextChanged != null) {
            onSearchTextChanged.onTextChanged(text);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (filterPopupMenu != null) {
            filterPopupMenu.dismiss();
            filterPopupMenu = null;
        }
        searchInputDisposable.clear();
        onSearchTextChanged = null;
        onFilterClickedListener = null;
        binding.vpGitRepos.setAdapter(null);
    }
}