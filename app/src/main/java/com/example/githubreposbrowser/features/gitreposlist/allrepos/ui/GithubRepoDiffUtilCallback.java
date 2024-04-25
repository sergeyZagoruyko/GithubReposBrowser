package com.example.githubreposbrowser.features.gitreposlist.allrepos.ui;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

import com.example.githubreposbrowser.features.gitreposlist.allrepos.domain.GithubRepo;

import java.util.Objects;

public class GithubRepoDiffUtilCallback extends DiffUtil.ItemCallback<GithubRepo> {

    @Override
    public boolean areItemsTheSame(@NonNull GithubRepo oldItem, @NonNull GithubRepo newItem) {
        return oldItem.id() == newItem.id();
    }

    @Override
    public boolean areContentsTheSame(@NonNull GithubRepo oldItem, @NonNull GithubRepo newItem) {
        return Objects.equals(oldItem, newItem);
    }
}
