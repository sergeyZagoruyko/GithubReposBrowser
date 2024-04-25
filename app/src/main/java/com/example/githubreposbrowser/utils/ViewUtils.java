package com.example.githubreposbrowser.utils;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;

import com.example.githubreposbrowser.listeners.OnTextChangedListener;

import java.util.concurrent.TimeUnit;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableOnSubscribe;
import io.reactivex.rxjava3.disposables.Disposable;

abstract public class ViewUtils {

    private static final long SEARCH_INPUT_DEBOUNCE_MS = 250;

    private ViewUtils() {
    }

    public static Disposable initAsyncTextChangeHandling(SearchView searchView, OnTextChangedListener callback) {
        return Observable.create((ObservableOnSubscribe<String>) emitter -> searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                    @Override
                    public boolean onQueryTextSubmit(String query) {
                        if (query != null && !emitter.isDisposed()) {
                            emitter.onNext(query);
                        }
                        return false;
                    }

                    @Override
                    public boolean onQueryTextChange(String newText) {
                        if (newText != null && !emitter.isDisposed()) {
                            emitter.onNext(newText);
                        }
                        return false;
                    }
                }))
                .debounce(SEARCH_INPUT_DEBOUNCE_MS, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(callback::onTextChanged);
    }

    public static void setVisibleOrGone(@NonNull final View view, final boolean visibleOrGone) {
        view.setVisibility(visibleOrGone ? View.VISIBLE : View.GONE);
    }
}
