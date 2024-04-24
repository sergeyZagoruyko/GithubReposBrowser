package com.example.githubreposbrowser.features.gitreposlist.allrepos.domain;

import com.example.githubreposbrowser.features.gitreposlist.allrepos.repository.GithubRepoResponse;

import javax.inject.Inject;

public class GithubRepoMapper {

    @Inject
    public GithubRepoMapper() {
    }

    public GithubRepo mapToUI(GithubRepoResponse responseItem) {
        return new GithubRepo(
                responseItem.id(),
                responseItem.getOwnerLogin(),
                responseItem.name(),
                responseItem.description(),
                responseItem.getAvatarUrl(),
                responseItem.startsCount());
    }
}
