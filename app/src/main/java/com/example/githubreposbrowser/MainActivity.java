package com.example.githubreposbrowser;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.example.githubreposbrowser.databinding.ActivityMainBinding;

public class MainActivity extends BaseActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
    }
}
