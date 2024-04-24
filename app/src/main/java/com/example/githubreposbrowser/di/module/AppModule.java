package com.example.githubreposbrowser.di.module;

import android.content.Context;

import com.example.githubreposbrowser.base.App;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
abstract public class AppModule {

    @Singleton
    @Provides
    public static Context provideAppContext(App application) {
        return application.getApplicationContext();
    }
}
