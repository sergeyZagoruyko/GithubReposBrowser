package com.example.githubreposbrowser.di.module;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Room;

import com.example.githubreposbrowser.db.ReposDB;
import com.example.githubreposbrowser.features.gitreposlist.favorites.FavoritesReposDao;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class DbModule {

    @Singleton
    @Provides
    ReposDB providesReposDB(@NonNull final Context context) {
        return Room.databaseBuilder(context, ReposDB.class, ReposDB.DB_NAME).build();
    }

    @Singleton
    @Provides
    FavoritesReposDao provideFavoritesReposDao(@NonNull final ReposDB db) {
        return db.favoritesReposDao();
    }
}
