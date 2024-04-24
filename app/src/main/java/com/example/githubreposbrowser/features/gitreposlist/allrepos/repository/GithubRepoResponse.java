package com.example.githubreposbrowser.features.gitreposlist.allrepos.repository;

import com.google.gson.annotations.SerializedName;

public record GithubRepoResponse(@SerializedName("id") long id, @SerializedName("owner") OwnerResponse owner,
                                 @SerializedName("name") String name,
                                 @SerializedName("description") String description,
                                 @SerializedName("stargazers_count") int startsCount) {

    @Override
    public long id() {
        return id;
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public String description() {
        return description;
    }

    @Override
    public int startsCount() {
        return startsCount;
    }

    public String getOwnerLogin() {
        return owner.login();
    }

    public String getAvatarUrl() {
        return owner.avatarUrl();
    }
}

record OwnerResponse(@SerializedName("login") String login, @SerializedName("avatar_url") String avatarUrl) {

    @Override
    public String login() {
        return login;
    }

    @Override
    public String avatarUrl() {
        return avatarUrl;
    }
}
