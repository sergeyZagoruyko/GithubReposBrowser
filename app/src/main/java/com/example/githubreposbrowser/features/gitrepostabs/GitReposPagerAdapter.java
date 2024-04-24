package com.example.githubreposbrowser.features.gitrepostabs;

import androidx.annotation.NonNull;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.githubreposbrowser.R;
import com.example.githubreposbrowser.features.gitreposlist.allrepos.ui.ReposListFragment;
import com.example.githubreposbrowser.features.gitreposlist.FavoriteReposListFragment;

public class GitReposPagerAdapter extends FragmentStateAdapter {

    public GitReposPagerAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return getTabTypeByPos(position).getFragment();
    }

    @Override
    public int getItemCount() {
        return PageType.values().length;
    }

    public PageType getTabTypeByPos(final int position) {
        return PageType.values()[position];
    }

    public enum PageType {
        ALL_REPOS(R.string.all_repos_tab_title),
        FAVORITES(R.string.favorite_repos_tab_title);

        @StringRes
        private final int tabNameRes;

        PageType(@StringRes int tabNameRes) {
            this.tabNameRes = tabNameRes;
        }

        public int getTabNameRes() {
            return tabNameRes;
        }

        public Fragment getFragment() {
            if (this == ALL_REPOS) {
                return ReposListFragment.newInstance();
            } else if (this == FAVORITES) {
                return FavoriteReposListFragment.newInstance();
            } else {
                return ReposListFragment.newInstance();
            }
        }
    }
}
