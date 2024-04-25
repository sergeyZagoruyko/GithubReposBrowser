package com.example.githubreposbrowser.features.gitreposlist.allrepos.data;

import com.google.gson.annotations.SerializedName;

public record GithubRepoResponse(@SerializedName("id") long id, @SerializedName("owner") OwnerResponse owner,
                                 @SerializedName("name") String name,
                                 @SerializedName("description") String description,
                                 @SerializedName("stargazers_count") int starsCount) {

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

    public int starsCount() {
        return starsCount;
    }

    public String getOwnerLogin() {
        return owner.login();
    }

    public String getAvatarUrl() {
        return owner.avatarUrl();
    }
}
