package com.example.githubreposbrowser.features.gitreposlist.allrepos.domain;

import androidx.annotation.NonNull;

public record GithubRepo(long id, @NonNull String ownerLogin, @NonNull String repoName, @NonNull String description,
                         @NonNull String avatarUrl, int starsCount) {

}
