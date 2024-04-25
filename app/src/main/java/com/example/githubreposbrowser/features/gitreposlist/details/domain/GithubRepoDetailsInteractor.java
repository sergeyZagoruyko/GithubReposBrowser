package com.example.githubreposbrowser.features.gitreposlist.details.domain;

import androidx.annotation.NonNull;

import com.example.githubreposbrowser.base.BaseInteractor;
import com.example.githubreposbrowser.features.gitreposlist.allrepos.data.GithubRepoRepository;
import com.example.githubreposbrowser.features.gitreposlist.allrepos.domain.GithubRepoMapper;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Single;

public class GithubRepoDetailsInteractor extends BaseInteractor {

    @NonNull
    private final GithubRepoRepository repository;

    @NonNull
    private final GithubRepoMapper mapper;

    @Inject
    public GithubRepoDetailsInteractor(@NonNull final GithubRepoRepository repository, @NonNull final GithubRepoMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public Single<GithubRepoDetails> getRepoDetails(final long id) {
        return repository.getRepoDetails(id)
                .map(mapper::mapDetailsToUI)
                .singleOrError();
    }
}
