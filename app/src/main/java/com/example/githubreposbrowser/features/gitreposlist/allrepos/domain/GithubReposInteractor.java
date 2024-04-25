package com.example.githubreposbrowser.features.gitreposlist.allrepos.domain;

import static com.example.githubreposbrowser.utils.Constants.PLUS_SYMBOL;

import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.githubreposbrowser.base.BaseInteractor;
import com.example.githubreposbrowser.features.gitreposlist.allrepos.data.GithubRepoRepository;
import com.example.githubreposbrowser.features.gitreposlist.allrepos.ui.GitReposFilterType;

import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Single;

public class GithubReposInteractor extends BaseInteractor {

    @NonNull
    private final GithubRepoRepository repository;

    @NonNull
    private final GithubRepoMapper mapper;

    @NonNull
    private final FilterDateConvertor filterDateConvertor;

    @Inject
    public GithubReposInteractor(@NonNull final GithubRepoRepository repository, @NonNull final GithubRepoMapper mapper,
                                 @NonNull final FilterDateConvertor filterDateConvertor) {
        this.repository = repository;
        this.mapper = mapper;
        this.filterDateConvertor = filterDateConvertor;
    }

    public Single<GithubRepoListData> searchGithubRepos(@Nullable final String query,
                                                        @NonNull final GitReposFilterType filterType, final int page) {
        return repository.searchGithubRepos(buildSearchRequestQuery(query, filterType), page)
                .map(response -> {
                    List<GithubRepo> repos = response.items()
                            .stream()
                            .map(mapper::mapToUI)
                            .collect(Collectors.toList());
                    return new GithubRepoListData(response.totalCount(), repos);
                })
                .singleOrError();
    }

    @NonNull
    private String buildSearchRequestQuery(@Nullable final String query, @NonNull final GitReposFilterType filterType) {
        final StringBuilder resultQueryBuilder = new StringBuilder();
        final String filterDateQuery = filterDateConvertor.getDateRange(filterType);

        if (!TextUtils.isEmpty(query)) {
            resultQueryBuilder.append(query).append(PLUS_SYMBOL);
        }
        return resultQueryBuilder.append(filterDateQuery).toString();
    }
}
