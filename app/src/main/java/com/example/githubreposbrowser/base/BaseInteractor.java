package com.example.githubreposbrowser.base;

import androidx.annotation.StringRes;

import com.example.githubreposbrowser.utils.ResourceManager;

import javax.inject.Inject;

abstract public class BaseInteractor {

    @Inject
    ResourceManager resourceManager;

    public String getString(@StringRes final int resId) {
        return resourceManager.getString(resId);
    }
}
