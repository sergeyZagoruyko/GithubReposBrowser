package com.example.githubreposbrowser.view;

import com.example.githubreposbrowser.listeners.OnItemSelectedListener;
import com.example.githubreposbrowser.listeners.OnTextChangedListener;

public interface SearchBarHolder {
    void setOnTextChangeListener(final OnTextChangedListener listener);

    <T> void setOnFilterClickedListener(final OnItemSelectedListener<T> listener);

    void setSearchItemsVisibility(final boolean visible);
}
