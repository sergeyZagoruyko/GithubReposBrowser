package com.example.githubreposbrowser.features.gitreposlist.allrepos.repository;

import androidx.annotation.NonNull;

import com.example.githubreposbrowser.api.ApiClient;
import com.example.githubreposbrowser.base.BaseRepository;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Observable;

public class GithubRepoRepository extends BaseRepository {

    private static final String SORTING_FIELD_TYPE = "stars";
    private static final String SORTING_ORDER = "desc";
    private static final int PAGE_SIZE = 20;

    @NonNull
    private final ApiClient apiClient;

    @Inject
    public GithubRepoRepository(@NonNull final ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public Observable<GithubRepoListResponse> searchGithubRepos(@NonNull final String searchQuery, final int page) {
        return apiClient.searchRepositories(searchQuery, SORTING_FIELD_TYPE, SORTING_ORDER, PAGE_SIZE, page);
    }
}
