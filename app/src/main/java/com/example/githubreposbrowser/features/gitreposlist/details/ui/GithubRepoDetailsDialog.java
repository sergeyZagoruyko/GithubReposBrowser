package com.example.githubreposbrowser.features.gitreposlist.details.ui;

import static com.example.githubreposbrowser.utils.ViewUtils.setVisibleOrGone;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.example.githubreposbrowser.R;
import com.example.githubreposbrowser.base.BaseBottomSheetDialog;
import com.example.githubreposbrowser.data.ScreenState;
import com.example.githubreposbrowser.databinding.BottomDialogGithubRepoDetailsBinding;
import com.example.githubreposbrowser.di.component.AppComponent;
import com.example.githubreposbrowser.features.gitreposlist.allrepos.domain.GithubRepo;
import com.example.githubreposbrowser.features.gitreposlist.details.di.GithubRepoDetailsDialogComponent;
import com.example.githubreposbrowser.features.gitreposlist.details.domain.GithubRepoDetails;
import com.example.githubreposbrowser.features.gitreposlist.details.impl.GithubRepoDetailViewModel;
import com.example.githubreposbrowser.features.gitreposlist.favorites.impl.FavoriteReposViewModel;

public class GithubRepoDetailsDialog extends BaseBottomSheetDialog {

    private static final String ARG_GITHUB_REPO_ID = "ARG_GITHUB_REPO_ID";
    private static final String ARG_IS_FAVORITE = "ARG_IS_FAVORITE";

    private BottomDialogGithubRepoDetailsBinding binding;
    private GithubRepoDetailViewModel viewModel;
    private FavoriteReposViewModel sharedFavoritesViewModel;

    private long githubRepoId;
    private boolean favorite;

    @NonNull
    public static GithubRepoDetailsDialog newInstance(final long githubRepoId, final boolean favorite) {
        final GithubRepoDetailsDialog fragment = new GithubRepoDetailsDialog();
        final Bundle args = new Bundle();
        args.putLong(ARG_GITHUB_REPO_ID, githubRepoId);
        args.putBoolean(ARG_IS_FAVORITE, favorite);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        binding = BottomDialogGithubRepoDetailsBinding.inflate(inflater, null, false);
        if (getArguments() != null) {
            githubRepoId = getArguments().getLong(ARG_GITHUB_REPO_ID, 0);
            favorite = getArguments().getBoolean(ARG_IS_FAVORITE);
        }
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel.onViewCreated(githubRepoId);
    }

    @Override
    protected void setupDI(AppComponent appComponent) {
        super.setupDI(appComponent);
        GithubRepoDetailsDialogComponent component = appComponent.plusRepoDetailsDialog().create();
        component.inject(this);
        viewModel = new ViewModelProvider(this, component.vm()).get(GithubRepoDetailViewModel.class);
        sharedFavoritesViewModel = new ViewModelProvider(requireActivity(), component.favoritesSharedVM()).get(FavoriteReposViewModel.class);
    }

    @Override
    protected void setupObservers() {
        super.setupObservers();
        observeNonNull(viewModel.screenState, this::onScreenStateChanged);
        observeNonNull(sharedFavoritesViewModel.favoriteStateChanged, detailsDialogData -> setFavoriteState(detailsDialogData.favorite()));
    }

    private void onScreenStateChanged(@NonNull final ScreenState state) {
        if (state instanceof ScreenState.SuccessResult) {
            setSuccessState(((ScreenState.SuccessResult<GithubRepoDetails>) state).getData());
        } else if (state instanceof ScreenState.Error) {
            setErrorState(((ScreenState.Error) state).getError());
        } else if (state instanceof ScreenState.Loading loadingState) {
            setLoadingState(loadingState.isLoading());
        } else if (state instanceof ScreenState.Empty) {
            setEmptyState();
        }
    }

    private void setSuccessState(@NonNull final GithubRepoDetails details) {
        binding.pbDetails.setVisibility(View.GONE);
        setDetailsUI(details);
    }

    private void setErrorState(@NonNull final String error) {
        binding.pbDetails.setVisibility(View.GONE);
        Toast.makeText(requireContext(), error, Toast.LENGTH_LONG).show();
    }

    private void setLoadingState(final boolean loading) {
        setVisibleOrGone(binding.pbDetails, loading);
    }

    private void setEmptyState() {
        binding.pbDetails.setVisibility(View.GONE);
    }

    private void setDetailsUI(@NonNull final GithubRepoDetails details) {
        final GithubRepo basicInfo = details.basicInfo();
        Glide.with(binding.getRoot().getContext())
                .load(basicInfo.avatarUrl())
                .placeholder(R.drawable.ic_launcher_background)
                .into(binding.ivAvatar);
        binding.tvOwnerLogin.setText(basicInfo.ownerLogin());
        binding.tvRepoName.setText(basicInfo.repoName());
        binding.tvRepoDesc.setText(basicInfo.description());
        binding.tvStarsCount.setText(String.valueOf(basicInfo.starsCount()));

        binding.tvRepoUrl.setText(details.repoUrl());
        final int forksCount = details.forksCount();
        binding.tvForksCount.setText(getResources().getQuantityString(R.plurals.details_forks_count, forksCount, forksCount));


        binding.tvCreationDate.setText(details.creationDate());

        final boolean progLangStrExists = !TextUtils.isEmpty(details.progLang());
        setVisibleOrGone(binding.tvProgLang, progLangStrExists);
        if (progLangStrExists) {
            binding.tvProgLang.setText(getString(R.string.details_prog_lang_prefix, details.progLang()));
        }

        setFavoriteState(favorite);
        binding.ivFavorite.setOnClickListener(v -> sharedFavoritesViewModel.onFavoriteClicked(details, favorite));
    }

    private void setFavoriteState(final boolean favorite) {
        this.favorite = favorite;
        binding.ivFavorite.setImageResource(favorite ? R.drawable.ic_favorite : R.drawable.ic_favorite_border);
    }
}
