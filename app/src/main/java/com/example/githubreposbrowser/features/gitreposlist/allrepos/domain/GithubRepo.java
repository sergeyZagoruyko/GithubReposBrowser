package com.example.githubreposbrowser.features.gitreposlist.allrepos.domain;

import androidx.annotation.NonNull;

public record GithubRepo(long id, @NonNull String ownerLogin, @NonNull String repoName, @NonNull String description,
                         @NonNull String avatarUrl, int starsCount) {

    private static final int LOADER_ID = -1;

    public boolean isLoader() {
        return id == LOADER_ID;
    }

    public static GithubRepo newInstanceLoader() {
        return new GithubRepo(-1, "", "", "", "", 0);
    }
}
