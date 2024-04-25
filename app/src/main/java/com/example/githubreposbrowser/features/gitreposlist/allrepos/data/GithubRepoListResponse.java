package com.example.githubreposbrowser.features.gitreposlist.allrepos.data;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public record GithubRepoListResponse(@SerializedName("total_count") int totalCount,
                                     @SerializedName("items") List<GithubRepoResponse> items) {

    @Override
    public List<GithubRepoResponse> items() {
        return items;
    }

    @Override
    public int totalCount() {
        return totalCount;
    }
}
