package com.example.githubreposbrowser.listeners;

@FunctionalInterface
public interface onItemSelectedListener<T> {
    void onItemSelected(final T item);
}
