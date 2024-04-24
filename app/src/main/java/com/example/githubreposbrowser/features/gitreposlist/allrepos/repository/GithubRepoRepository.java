package com.example.githubreposbrowser.features.gitreposlist.allrepos.repository;

import androidx.annotation.NonNull;

import com.example.githubreposbrowser.api.ApiClient;
import com.example.githubreposbrowser.base.BaseRepository;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Observable;

public class GithubRepoRepository extends BaseRepository {

    @NonNull
    private final ApiClient apiClient;

    @Inject
    public GithubRepoRepository(@NonNull final ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    // TODO: Replace mocking code with impl once UI is ready
    public Observable<List<GithubRepoResponse>> searchGithubRepos() {
        return apiClient.searchRepositories("created:>2024-04-17", 20, 1).
                map(GithubRepoListResponse::items);
    }
}
