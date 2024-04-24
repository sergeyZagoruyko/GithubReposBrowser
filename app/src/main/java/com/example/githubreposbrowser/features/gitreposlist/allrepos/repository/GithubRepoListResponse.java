package com.example.githubreposbrowser.features.gitreposlist.allrepos.repository;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public record GithubRepoListResponse(@SerializedName("items") List<GithubRepoResponse> items) {

    @Override
    public List<GithubRepoResponse> items() {
        return items;
    }
}
