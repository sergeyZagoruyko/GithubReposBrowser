package com.example.githubreposbrowser.features.gitreposlist.allrepos.domain;

import androidx.annotation.NonNull;

import com.example.githubreposbrowser.base.BaseInteractor;
import com.example.githubreposbrowser.features.gitreposlist.allrepos.repository.GithubRepoRepository;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Single;

public class GithubReposInteractor extends BaseInteractor {

    @NonNull
    private final GithubRepoRepository repository;

    @NonNull
    private final GithubRepoMapper mapper;

    @Inject
    public GithubReposInteractor(@NonNull final GithubRepoRepository repository, @NonNull final GithubRepoMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public Single<List<GithubRepo>> searchGithubRepos() {
        return repository.searchGithubRepos()
                .flatMapIterable(list -> list)
                .map(mapper::mapFromResponse)
                .toList();
    }
}
