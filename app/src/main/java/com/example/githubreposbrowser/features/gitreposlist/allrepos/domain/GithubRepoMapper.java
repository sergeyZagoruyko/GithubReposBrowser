package com.example.githubreposbrowser.features.gitreposlist.allrepos.domain;

import android.text.TextUtils;

import com.example.githubreposbrowser.R;
import com.example.githubreposbrowser.features.gitreposlist.allrepos.repository.GithubRepoResponse;
import com.example.githubreposbrowser.utils.ResourceManager;

import javax.inject.Inject;

import io.reactivex.rxjava3.annotations.NonNull;

public class GithubRepoMapper {

    @NonNull
    private final ResourceManager resManager;

    @Inject
    public GithubRepoMapper(@NonNull final ResourceManager resManager) {
        this.resManager = resManager;
    }

    public GithubRepo mapToUI(GithubRepoResponse responseItem) {
        final String description = responseItem.description();
        return new GithubRepo(
                responseItem.id(),
                responseItem.getOwnerLogin(),
                responseItem.name(),
                !TextUtils.isEmpty(description) ? description : resManager.getString(R.string.repo_description_default_mock),
                responseItem.getAvatarUrl(),
                responseItem.startsCount());
    }
}
