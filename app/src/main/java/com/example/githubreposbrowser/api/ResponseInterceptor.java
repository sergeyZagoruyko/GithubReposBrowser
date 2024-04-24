package com.example.githubreposbrowser.api;

import android.util.Log;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class ResponseInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        // TODO: Handle invalid token case
        Response response = chain.proceed(chain.request());
      /*  if (response.body() != null) {
            String rawJson = response.peekBody(Long.MAX_VALUE).string();
        }*/
        return response;
    }
}