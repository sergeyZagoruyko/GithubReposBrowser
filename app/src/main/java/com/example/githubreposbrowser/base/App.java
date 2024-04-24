package com.example.githubreposbrowser.base;

import android.app.Application;

import com.example.githubreposbrowser.di.component.AppComponent;
import com.example.githubreposbrowser.di.component.DaggerAppComponent;

public class App extends Application {

    private static AppComponent component;

    @Override
    public void onCreate() {
        super.onCreate();
        setupDI();
    }

    public static AppComponent getComponent() {
        return component;
    }

    private void setupDI() {
        component = DaggerAppComponent.factory().create(this);
    }
}
