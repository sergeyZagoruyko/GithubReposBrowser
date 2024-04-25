package com.example.githubreposbrowser.api;

import androidx.annotation.NonNull;

import com.example.githubreposbrowser.base.App;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Response;

public class ExpiredTokenInterceptor implements Interceptor {

    private static final int INVALID_TOKEN_STATUS_CODE = 403;

    @NonNull
    @Override
    public Response intercept(Chain chain) throws IOException {
        final Response response = chain.proceed(chain.request());
        App.getInstance().onExpiredTokenStatusChanged(response.code() == INVALID_TOKEN_STATUS_CODE);
        return response;
    }
}
