package com.example.githubreposbrowser.utils;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

abstract public class LiveDataUtils {

    private LiveDataUtils() {
    }

    public static <T> void observeNonNull(@NonNull final LiveData<T> liveData, @NonNull final LifecycleOwner owner, @NonNull final Observer<T> observer) {
        liveData.observe(owner, t -> {
            if (t != null) {
                observer.onChanged(t);
            }
        });
    }
}
