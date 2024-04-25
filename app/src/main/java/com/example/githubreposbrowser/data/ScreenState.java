package com.example.githubreposbrowser.data;

import androidx.annotation.NonNull;

public abstract class ScreenState {

    private ScreenState() {
    }

    public static final class SuccessResult<T> extends ScreenState {

        private final T data;

        SuccessResult(@NonNull final T data) {
            this.data = data;
        }

        @NonNull
        public T getData() {
            return data;
        }
    }

    public static final class Error extends ScreenState {

        private final String error;

        Error(@NonNull final String error) {
            this.error = error;
        }

        @NonNull
        public String getError() {
            return error;
        }
    }

    public static final class Loading extends ScreenState {

        private final boolean loading;
        private final boolean refreshing;

        Loading(final boolean loading, final boolean refreshing) {
            this.loading = loading;
            this.refreshing = refreshing;
        }

        public boolean isLoading() {
            return loading;
        }

        public boolean isRefreshing() {
            return refreshing;
        }
    }

    public static final class Empty extends ScreenState {
    }

    public static <T> ScreenState success(@NonNull final T data) {
        return new SuccessResult<T>(data);
    }

    public static ScreenState error(@NonNull final String error) {
        return new Error(error);
    }

    public static ScreenState loading(final boolean loading, final boolean refreshing) {
        return new Loading(loading, refreshing);
    }

    public static ScreenState empty() {
        return new ScreenState.Empty();
    }
}