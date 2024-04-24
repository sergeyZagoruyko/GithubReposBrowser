package com.example.githubreposbrowser.api;

import com.example.githubreposbrowser.features.gitreposlist.allrepos.repository.GithubRepoListResponse;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiClient {
    @GET("search/repositories")
    Observable<GithubRepoListResponse> searchRepositories(
            @Query(value = "q") String query,
            //@Query("sort") String sort,
        //    @Query("order") String order,
            @Query("per_page") int perPage,
            @Query("page") int page
    );
}
