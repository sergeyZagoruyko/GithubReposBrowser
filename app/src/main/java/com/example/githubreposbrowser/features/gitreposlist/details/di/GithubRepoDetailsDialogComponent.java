package com.example.githubreposbrowser.features.gitreposlist.details.di;

import androidx.annotation.NonNull;

import com.example.githubreposbrowser.di.scope.PerFragment;
import com.example.githubreposbrowser.features.gitreposlist.details.impl.GithubRepoDetailViewModel;
import com.example.githubreposbrowser.features.gitreposlist.details.ui.GithubRepoDetailsDialog;
import com.example.githubreposbrowser.utils.ViewModelFactory;

import dagger.Subcomponent;

@PerFragment
@Subcomponent
public interface GithubRepoDetailsDialogComponent {

    @Subcomponent.Factory
    interface Factory {
        GithubRepoDetailsDialogComponent create();
    }

    ViewModelFactory<GithubRepoDetailViewModel> vm();

    void inject(@NonNull final GithubRepoDetailsDialog fragment);
}