package com.example.githubreposbrowser.features.gitreposlist.allrepos.ui;

import static com.example.githubreposbrowser.utils.ViewUtils.setVisibleOrGone;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.githubreposbrowser.R;
import com.example.githubreposbrowser.base.BaseFragment;
import com.example.githubreposbrowser.data.ScreenState;
import com.example.githubreposbrowser.databinding.FragmentGitReposListBinding;
import com.example.githubreposbrowser.di.component.AppComponent;
import com.example.githubreposbrowser.features.SearchBarHolder;
import com.example.githubreposbrowser.features.gitreposlist.allrepos.di.RepoListFrmComponent;
import com.example.githubreposbrowser.features.gitreposlist.allrepos.domain.GithubRepo;
import com.example.githubreposbrowser.features.gitreposlist.allrepos.impl.ReposListViewModel;
import com.example.githubreposbrowser.listeners.onItemSelectedListener;

import java.util.List;

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
        observeNonNull(viewModel.screenState, this::onScreenStateChanged);
    }

    @Override
    protected void setupListeners() {
        super.setupListeners();
        if (searchBarHolder != null) {
            searchBarHolder.setOnTextChangeListener(text -> viewModel.onSearchTextEntered(text));
            searchBarHolder.setOnFilterClickedListener(viewModel::onFilterItemSelected);
            searchBarHolder.setOnFilterClickedListener((onItemSelectedListener<GitReposFilterType>) item ->
                    viewModel.onFilterItemSelected(item));
        }
        binding.swipeRefreshRepos.setOnRefreshListener(() -> viewModel.onRefreshRepos());
    }

    private void initUI() {
        binding.rvGithubRepos.setAdapter(adapter);
    }

    private void onScreenStateChanged(@NonNull final ScreenState state) {
        if (state instanceof ScreenState.SuccessResult) {
            setSuccessState(((ScreenState.SuccessResult<List<GithubRepo>>) state).getData());
        } else if (state instanceof ScreenState.Error) {
            setErrorState(((ScreenState.Error) state).getError());
        } else if (state instanceof final ScreenState.Loading loadingState) {
            setLoadingState(loadingState.isLoading(), loadingState.isRefreshing());

        }
    }

    private void setSuccessState(@NonNull final List<GithubRepo> list) {
        adapter.submitList(list);

        binding.swipeRefreshRepos.setRefreshing(false);
        binding.rvGithubRepos.setVisibility(View.VISIBLE);
        binding.groupErrorState.setVisibility(View.GONE);
        binding.pbRepos.setVisibility(View.GONE);
    }

    private void setErrorState(@NonNull final String error) {
        binding.swipeRefreshRepos.setRefreshing(false);
        binding.rvGithubRepos.setVisibility(View.GONE);
        binding.groupErrorState.setVisibility(View.VISIBLE);
        binding.pbRepos.setVisibility(View.GONE);

        binding.tvErrorState.setText(getString(R.string.error_data_loading_schema, error));
    }

    private void setLoadingState(final boolean loading, final boolean refreshing) {
        setVisibleOrGone(binding.pbRepos, loading);
        binding.swipeRefreshRepos.setRefreshing(refreshing);

        binding.rvGithubRepos.setVisibility(View.GONE);
        binding.groupErrorState.setVisibility(View.GONE);
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
            searchBarHolder.setOnFilterClickedListener(null);
        }
        binding.rvGithubRepos.setAdapter(null);
    }
}
