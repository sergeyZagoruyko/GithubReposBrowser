package com.example.githubreposbrowser.features.gitreposlist.allrepos.ui;

import androidx.annotation.IdRes;

import com.example.githubreposbrowser.R;

public enum GitReposFilterType {

    LAST_DAY(R.id.actionTypeDay),
    LAST_WEEK(R.id.actionTypeWeek),
    LAST_MONTH(R.id.actionTypeMonth);

    @IdRes
    private final int popupItemId;

    GitReposFilterType(@IdRes final int popupItemId) {
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
