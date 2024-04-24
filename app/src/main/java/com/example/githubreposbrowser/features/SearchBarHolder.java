package com.example.githubreposbrowser.features;

import com.example.githubreposbrowser.listeners.onItemSelectedListener;
import com.example.githubreposbrowser.listeners.OnTextChangedListener;

public interface SearchBarHolder {
    void setOnTextChangeListener(final OnTextChangedListener listener);

    <T> void setOnFilterClickedListener(final onItemSelectedListener<T> listener);
}
