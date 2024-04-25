package com.example.githubreposbrowser.features.gitreposlist.details.domain;

import com.example.githubreposbrowser.features.gitreposlist.allrepos.domain.GithubRepo;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.annotations.Nullable;

public record GithubRepoDetails(@NonNull GithubRepo basicInfo, @NonNull String repoUrl, int forksCount,
                                @NonNull String creationDate,
                                @Nullable String progLang) {

    @Override
    public GithubRepo basicInfo() {
        return basicInfo;
    }

    @Override
    public String repoUrl() {
        return repoUrl;
    }

    @Override
    public int forksCount() {
        return forksCount;
    }

    @Override
    public String creationDate() {
        return creationDate;
    }

    @Override
    public String progLang() {
        return progLang;
    }
}
