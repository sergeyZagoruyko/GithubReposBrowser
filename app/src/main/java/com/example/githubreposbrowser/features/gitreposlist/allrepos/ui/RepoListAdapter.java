package com.example.githubreposbrowser.features.gitreposlist.allrepos.ui;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.githubreposbrowser.R;
import com.example.githubreposbrowser.databinding.ItemGithubRepoBinding;
import com.example.githubreposbrowser.databinding.ItemLoaderBinding;
import com.example.githubreposbrowser.features.gitreposlist.allrepos.domain.GithubRepo;
import com.example.githubreposbrowser.listeners.OnItemSelectedListener;
import com.example.githubreposbrowser.view.LoaderIListItemViewHolder;

public class RepoListAdapter extends ListAdapter<GithubRepo, RecyclerView.ViewHolder> {

    private static final int TYPE_ITEM = 0;
    private static final int TYPE_LOADER = 1;

    @NonNull
    protected final OnItemSelectedListener<GithubRepo> onItemSelectedListener;

    protected RepoListAdapter(@NonNull final OnItemSelectedListener<GithubRepo> onItemSelectedListener) {
        super(new GithubRepoDiffUtilCallback());
        this.onItemSelectedListener = onItemSelectedListener;
    }

    @Override
    public int getItemViewType(int position) {
        return (getCurrentList().size() - 1 >= position && !getCurrentList().get(position).isLoader())
                ? TYPE_ITEM : TYPE_LOADER;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        if (viewType == TYPE_ITEM) {
            final ItemGithubRepoBinding binding = ItemGithubRepoBinding.inflate(inflater, parent, false);
            return new ItemViewHolder(binding);
        } else {
            final ItemLoaderBinding binding = ItemLoaderBinding.inflate(inflater, parent, false);
            return new LoaderIListItemViewHolder(binding);
        }

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ItemViewHolder) {
            ((ItemViewHolder) holder).bind(getItem(position));
        }
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {

        @NonNull
        private final ItemGithubRepoBinding binding;

        public ItemViewHolder(@NonNull final ItemGithubRepoBinding binding) {
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

            binding.getRoot().setOnClickListener(v -> onItemSelectedListener.onItemSelected(item));
        }
    }

}
