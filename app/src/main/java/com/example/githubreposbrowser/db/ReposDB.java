package com.example.githubreposbrowser.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.githubreposbrowser.features.gitreposlist.GithubRepoEntity;
import com.example.githubreposbrowser.features.gitreposlist.favorites.data.FavoritesReposDao;

@Database(entities = {GithubRepoEntity.class}, version = ReposDB.DB_VERSION)
public abstract class ReposDB extends RoomDatabase {

    public static final String DB_NAME = "Repos db";
    static final int DB_VERSION = 1;

    public abstract FavoritesReposDao favoritesReposDao();
}
