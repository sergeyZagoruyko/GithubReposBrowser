package com.example.githubreposbrowser.features.gitreposlist;

import static com.example.githubreposbrowser.utils.ViewUtils.setVisibleOrGone;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.githubreposbrowser.R;
import com.example.githubreposbrowser.base.BaseFragment;
import com.example.githubreposbrowser.data.ScreenState;
import com.example.githubreposbrowser.databinding.FragmentGitReposListBinding;
import com.example.githubreposbrowser.view.SearchBarHolder;
import com.example.githubreposbrowser.features.gitreposlist.allrepos.BaseRepoViewModel;
import com.example.githubreposbrowser.features.gitreposlist.allrepos.domain.GithubRepo;
import com.example.githubreposbrowser.features.gitreposlist.allrepos.ui.RepoListAdapter;
import com.example.githubreposbrowser.features.gitreposlist.details.ui.GithubRepoDetailsDialog;

import java.util.List;

abstract public class BaseRepoListFragment extends BaseFragment {

    protected FragmentGitReposListBinding binding;

    @NonNull
    protected abstract BaseRepoViewModel getViewModel();

    @NonNull
    protected final RepoListAdapter adapter = new RepoListAdapter(item -> getViewModel().onItemSelected(item));

    @Nullable
    protected SearchBarHolder searchBarHolder = null;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
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
    protected void setupObservers() {
        super.setupObservers();
        observeNonNull(getViewModel().screenState, this::onScreenStateChanged);
        observeNonNull(getViewModel().showRepoDetailsDialog, detailsDialogData -> showRepoDetailsDialog(detailsDialogData.repoId(), detailsDialogData.favorite()));
    }

    protected void showRepoDetailsDialog(@NonNull final Long id, final boolean favorite) {
        final GithubRepoDetailsDialog dialog = GithubRepoDetailsDialog.newInstance(id, favorite);
        dialog.show(getParentFragmentManager(), this.getClass().getSimpleName());
    }

    @Nullable
    private SearchBarHolder getSearchBarHolderImpl() {
        if (getParentFragment() != null && getParentFragment() instanceof SearchBarHolder) {
            return (SearchBarHolder) getParentFragment();
        }
        return null;
    }

    protected void initUI() {
        binding.rvGithubRepos.setAdapter(adapter);
        binding.rvGithubRepos.setItemAnimator(null);
    }

    @Override
    protected void setupListeners() {
        super.setupListeners();
        binding.swipeRefreshRepos.setOnRefreshListener(() -> getViewModel().onRefreshRepos());
    }

    private void onScreenStateChanged(@NonNull final ScreenState state) {
        if (state instanceof ScreenState.SuccessResult) {
            setSuccessState(((ScreenState.SuccessResult<List<GithubRepo>>) state).getData());
        } else if (state instanceof ScreenState.Error) {
            setErrorState(((ScreenState.Error) state).getError());
        } else if (state instanceof ScreenState.Loading loadingState) {
            setLoadingState(loadingState.isLoading(), loadingState.isRefreshing());
        } else if (state instanceof ScreenState.Empty) {
            setEmptyState();
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

        binding.tvErrorState.setText(getString(R.string.error_data_loading, error));
    }

    private void setLoadingState(final boolean loading, final boolean refreshing) {
        setVisibleOrGone(binding.pbRepos, loading);
        binding.swipeRefreshRepos.setRefreshing(refreshing);

        binding.rvGithubRepos.setVisibility(View.GONE);
        binding.groupErrorState.setVisibility(View.GONE);
    }

    private void setEmptyState() {
        binding.swipeRefreshRepos.setRefreshing(false);
        binding.rvGithubRepos.setVisibility(View.GONE);
        binding.pbRepos.setVisibility(View.GONE);
        binding.groupErrorState.setVisibility(View.VISIBLE);
        binding.tvErrorState.setText(getString(R.string.empty_screen_state_text));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (searchBarHolder != null) {
            searchBarHolder.setOnTextChangeListener(null);
            searchBarHolder.setOnFilterClickedListener(null);
        }
    }
}
