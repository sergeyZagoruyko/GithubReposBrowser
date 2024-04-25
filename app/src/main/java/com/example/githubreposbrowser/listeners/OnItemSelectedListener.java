package com.example.githubreposbrowser.listeners;

@FunctionalInterface
public interface OnItemSelectedListener<T> {
    void onItemSelected(final T item);
}
