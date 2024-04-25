package com.example.githubreposbrowser.features.gitreposlist.allrepos.data;

import com.google.gson.annotations.SerializedName;

public record OwnerResponse(@SerializedName("login") String login, @SerializedName("avatar_url") String avatarUrl) {

    @Override
    public String login() {
        return login;
    }

    @Override
    public String avatarUrl() {
        return avatarUrl;
    }
}
