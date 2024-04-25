package com.example.githubreposbrowser.features.gitreposlist.details.domain;

import com.example.githubreposbrowser.features.gitreposlist.allrepos.data.OwnerResponse;
import com.google.gson.annotations.SerializedName;

public record GithubRepoDetailsResponse(@SerializedName("id") long id, @SerializedName("owner") OwnerResponse owner,
                                        @SerializedName("name") String name,
                                        @SerializedName("description") String description,
                                        @SerializedName("stargazers_count") int starsCount,
                                        @SerializedName("forks") int forksCount,
                                        @SerializedName("html_url") String repoUrl,
                                        @SerializedName("created_at") String creationDate,
                                        @SerializedName("language") String language) {

    @Override
    public long id() {
        return id;
    }

    @Override
    public OwnerResponse owner() {
        return owner;
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public String description() {
        return description;
    }

    public int starsCount() {
        return starsCount;
    }

    @Override
    public int forksCount() {
        return forksCount;
    }

    @Override
    public String repoUrl() {
        return repoUrl;
    }

    @Override
    public String creationDate() {
        return creationDate;
    }

    @Override
    public String language() {
        return language;
    }
}