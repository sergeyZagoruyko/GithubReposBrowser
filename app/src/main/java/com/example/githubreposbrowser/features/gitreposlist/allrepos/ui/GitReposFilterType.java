package com.example.githubreposbrowser.features.gitreposlist.allrepos.ui;

import androidx.annotation.IdRes;
import androidx.annotation.StringRes;

import com.example.githubreposbrowser.R;

public enum GitReposFilterType {

    LAST_DAY(R.string.git_repos_filter_type_last_day, R.id.actionTypeDay),
    LAST_WEEK(R.string.git_repos_filter_type_last_week, R.id.actionTypeWeek),
    LAST_MONTH(R.string.git_repos_filter_type_last_month, R.id.actionTypeMonth);

    @StringRes
    private final int typeNameResId;

    @IdRes
    private final int popupItemId;

    GitReposFilterType(@StringRes final int typeNameResId, @IdRes final int popupItemId) {
        this.typeNameResId = typeNameResId;
        this.popupItemId = popupItemId;
    }

    public static GitReposFilterType getDefaultValue() {
        return GitReposFilterType.LAST_DAY;
    }

    public static GitReposFilterType getByItemId(@IdRes final int popupItemId) {
        for (GitReposFilterType type : GitReposFilterType.values()) {
            if (type.popupItemId == popupItemId) {
                return type;
            }
        }
        return getDefaultValue();
    }
}
