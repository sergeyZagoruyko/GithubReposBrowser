package com.example.githubreposbrowser.utils;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.StringRes;

import javax.inject.Inject;

public class ResourceManager {

    @NonNull
    private final Context context;

    @Inject
    public ResourceManager(@NonNull final Context context) {
        this.context = context;
    }

    public String getString(@StringRes final int resId) {
        return context.getString(resId);
    }
}
