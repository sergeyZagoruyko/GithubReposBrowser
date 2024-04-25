package com.example.githubreposbrowser.features.gitreposlist.allrepos.domain;

import androidx.annotation.NonNull;

import java.util.List;

public record GithubRepoListData(int totalCount, @NonNull List<GithubRepo> items) {

    @Override
    public int totalCount() {
        return totalCount;
    }

    @Override
    @NonNull
    public List<GithubRepo> items() {
        return items;
    }
}
