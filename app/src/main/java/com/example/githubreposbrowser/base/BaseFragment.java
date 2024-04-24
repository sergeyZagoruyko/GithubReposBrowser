package com.example.githubreposbrowser.base;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.githubreposbrowser.di.component.AppComponent;

abstract public class BaseFragment extends Fragment {

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        setupDI(App.getComponent());
    }

    public abstract void setupDI(AppComponent appComponent);
}
