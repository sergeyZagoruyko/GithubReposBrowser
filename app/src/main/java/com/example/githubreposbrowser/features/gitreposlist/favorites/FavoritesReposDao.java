package com.example.githubreposbrowser.features.gitreposlist.favorites;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.githubreposbrowser.features.gitreposlist.GithubRepoEntity;

import java.util.List;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;

@Dao
public interface FavoritesReposDao {

    @Query("SELECT * FROM github_repo ORDER BY stars_count DESC")
    Observable<List<GithubRepoEntity>> getFavorites();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Single<Long> insert(@NonNull final GithubRepoEntity entity);

    @Query("DELETE FROM github_repo WHERE repo_id = :id")
    Completable delete(final long id);
}
