package com.example.githubreposbrowser.base;

import android.app.Application;

import androidx.annotation.Nullable;

import com.example.githubreposbrowser.di.component.AppComponent;
import com.example.githubreposbrowser.di.component.DaggerAppComponent;
import com.example.githubreposbrowser.listeners.OnExpiredTokenListener;

public class App extends Application {

    private static App instance;

    @Nullable
    private OnExpiredTokenListener expiredTokenListener;
    private static AppComponent component;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        setupDI();
    }

    public static AppComponent getComponent() {
        return component;
    }

    public static App getInstance() {
        return instance;
    }

    public void onExpiredTokenStatusChanged(final boolean expired) {
        if (expiredTokenListener != null) {
            expiredTokenListener.onTokenExpiredState(expired);
        }
    }

    public void setExpiredTokenListener(@Nullable OnExpiredTokenListener expiredTokenListener) {
        this.expiredTokenListener = expiredTokenListener;
    }

    private void setupDI() {
        component = DaggerAppComponent.factory().create(this);
    }
}
