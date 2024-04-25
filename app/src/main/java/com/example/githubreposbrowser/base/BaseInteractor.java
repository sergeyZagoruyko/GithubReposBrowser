package com.example.githubreposbrowser.base;

import androidx.annotation.NonNull;
import androidx.annotation.StringRes;

import com.example.githubreposbrowser.utils.ResourceManager;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import javax.inject.Inject;

import okhttp3.ResponseBody;
import retrofit2.HttpException;

abstract public class BaseInteractor {

    private static final String JSON_KEY_ERROR_MESSAGE = "message";

    @Inject
    ResourceManager resourceManager;

    @NonNull
    private final JsonParser jsonParser = new JsonParser();

    public String getString(@StringRes final int resId) {
        return resourceManager.getString(resId);
    }

    public String parseApiError(@NonNull final Throwable error, @NonNull final String defErrorText) {
        String errorContent = defErrorText;
        if (!(error instanceof HttpException)) {
            return defErrorText;
        }

        try {
            final ResponseBody errorBody = ((HttpException) error).response().errorBody();
            final String errorJson = errorBody.string();
            final JsonObject jsonObj = jsonParser.parse(errorJson).getAsJsonObject();
            if (jsonObj.has(JSON_KEY_ERROR_MESSAGE)) {
                errorContent = jsonObj.get(JSON_KEY_ERROR_MESSAGE).getAsString();
            }

        } catch (Exception ignored) {
        }

        return errorContent;
    }
}
