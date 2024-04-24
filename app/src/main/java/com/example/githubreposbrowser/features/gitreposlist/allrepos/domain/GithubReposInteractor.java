package com.example.githubreposbrowser.features.gitreposlist.allrepos.domain;

import static com.example.githubreposbrowser.utils.Constants.PLUS_SYMBOL;

import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.githubreposbrowser.base.BaseInteractor;
import com.example.githubreposbrowser.features.gitreposlist.allrepos.repository.GithubRepoRepository;

import java.util.List;
import java.util.concurrent.TimeUnit;

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

    public Single<List<GithubRepo>> searchGithubRepos(@Nullable final String query) {
        return repository.searchGithubRepos(buildSearchRequestQuery(query))
                .flatMapIterable(list -> list)
                .map(mapper::mapToUI)
                .toList();
    }

    @NonNull
    private String buildSearchRequestQuery(@Nullable final String query) {
        // TODO: Replace mocking code with impl once UI is ready
        final StringBuilder resultQueryBuilder = new StringBuilder();
        final String filterDateQuery = "created:>2024-04-17";

        if (!TextUtils.isEmpty(query)) {
            resultQueryBuilder.append(query).append(PLUS_SYMBOL);
        }
        return resultQueryBuilder.append(filterDateQuery).toString();
    }
}
