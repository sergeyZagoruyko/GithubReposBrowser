package com.example.githubreposbrowser.features.gitreposlist.favorites.domain;

import androidx.annotation.NonNull;

import com.example.githubreposbrowser.base.BaseInteractor;
import com.example.githubreposbrowser.features.gitreposlist.allrepos.domain.GithubRepo;
import com.example.githubreposbrowser.features.gitreposlist.allrepos.domain.GithubRepoMapper;
import com.example.githubreposbrowser.features.gitreposlist.favorites.data.FavoritesReposDao;

import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;

public class FavoritesInteractor extends BaseInteractor {

    @NonNull
    private final FavoritesReposDao favoritesReposDao;
    @NonNull
    private final GithubRepoMapper mapper;

    @Inject
    public FavoritesInteractor(@NonNull final FavoritesReposDao favoritesReposDao, @NonNull final GithubRepoMapper mapper) {
        this.favoritesReposDao = favoritesReposDao;
        this.mapper = mapper;
    }

    public Observable<List<GithubRepo>> getGithubRepos() {
        return favoritesReposDao.getFavorites()
                .map(githubRepoEntities -> githubRepoEntities.stream().map(mapper::mapFromEntity).collect(Collectors.toList()));
    }

    public Single<Long> addToFavorites(@NonNull final GithubRepo item) {
        return favoritesReposDao.insert(mapper.mapToEntity(item));
    }

    public Completable deleteFromFavorites(final long repoId) {
        return favoritesReposDao.delete(repoId);
    }
}
