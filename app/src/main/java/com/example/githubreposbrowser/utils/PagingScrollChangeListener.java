package com.example.githubreposbrowser.utils;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;

public class PagingScrollChangeListener implements View.OnScrollChangeListener {

    private static final int PAGING_THRESHOLD = 3;

    @NonNull
    private final LinearLayoutManager layoutManager;
    @NonNull
    private final PagingScrollNext listener;

    public PagingScrollChangeListener(@NonNull final LinearLayoutManager layoutManager,
                                      @NonNull final PagingScrollNext listener) {
        this.layoutManager = layoutManager;
        this.listener = listener;
    }

    @Override
    public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
        final int totalItemCount = layoutManager.getItemCount();
        final int lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition();

        if (totalItemCount - lastVisibleItemPosition <= PAGING_THRESHOLD) {
            listener.onScrolledToNext();
        }
    }

    @FunctionalInterface
    public interface PagingScrollNext {
        void onScrolledToNext();
    }
}
