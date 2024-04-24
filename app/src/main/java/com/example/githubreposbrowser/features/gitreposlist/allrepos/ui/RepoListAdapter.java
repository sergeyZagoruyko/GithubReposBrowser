package com.example.githubreposbrowser.features.gitreposlist.allrepos.ui;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.githubreposbrowser.R;
import com.example.githubreposbrowser.databinding.ItemGithubRepoBinding;
import com.example.githubreposbrowser.features.gitreposlist.allrepos.domain.GithubRepo;

public class RepoListAdapter extends ListAdapter<GithubRepo, RepoListAdapter.ViewHolder> {

    protected RepoListAdapter() {
        super(new GithubRepoDiffUtilCallback());
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        final ItemGithubRepoBinding binding = ItemGithubRepoBinding.inflate(inflater, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(getItem(position));
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        @NonNull
        private final ItemGithubRepoBinding binding;

        public ViewHolder(@NonNull final ItemGithubRepoBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(GithubRepo item) {
            Glide.with(binding.getRoot().getContext())
                    .load(item.avatarUrl())
                    .placeholder(R.drawable.ic_launcher_background)
                    .into(binding.ivAvatar);
            binding.tvOwnerLogin.setText(item.ownerLogin());
            binding.tvRepoName.setText(item.repoName());
            binding.tvRepoDesc.setText(item.description());
            binding.tvStarsCount.setText(String.valueOf(item.starsCount()));
        }
    }
}
