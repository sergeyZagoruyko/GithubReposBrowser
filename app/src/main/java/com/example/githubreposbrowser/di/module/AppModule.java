package com.example.githubreposbrowser.di.module;

import static com.example.githubreposbrowser.utils.Constants.API_BASE_URL;

import android.content.Context;

import com.example.githubreposbrowser.api.ApiClient;
import com.example.githubreposbrowser.api.ExpiredTokenInterceptor;
import com.example.githubreposbrowser.base.App;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
abstract public class AppModule {

    @Singleton
    @Provides
    public static Context provideAppContext(App application) {
        return application.getApplicationContext();
    }

    @Singleton
    @Provides
    public static ApiClient provideApiClient() {
        return new Retrofit.Builder()
                .baseUrl(API_BASE_URL)
                .client(new OkHttpClient.Builder()
                        .addInterceptor(new ExpiredTokenInterceptor())
                        .build())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .build()
                .create(ApiClient.class);
    }
}
