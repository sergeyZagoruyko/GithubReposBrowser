package com.example.githubreposbrowser.features.gitreposlist.allrepos.domain;

import android.text.TextUtils;

import com.example.githubreposbrowser.R;
import com.example.githubreposbrowser.features.gitreposlist.GithubRepoEntity;
import com.example.githubreposbrowser.features.gitreposlist.allrepos.data.GithubRepoResponse;
import com.example.githubreposbrowser.features.gitreposlist.details.domain.GithubRepoDetails;
import com.example.githubreposbrowser.features.gitreposlist.details.domain.GithubRepoDetailsResponse;
import com.example.githubreposbrowser.utils.ResourceManager;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;

import javax.inject.Inject;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.annotations.Nullable;

public class GithubRepoMapper {

    @NonNull
    private final ResourceManager resManager;

    @Inject
    public GithubRepoMapper(@NonNull final ResourceManager resManager) {
        this.resManager = resManager;
    }

    @NonNull
    public GithubRepo mapToUI(@NonNull final GithubRepoResponse responseItem) {
        final String description = responseItem.description();
        return mapToUIByFields(responseItem.id(), responseItem.getOwnerLogin(), responseItem.name(),
                !TextUtils.isEmpty(description) ? description : resManager.getString(R.string.repo_description_default_mock),
                responseItem.getAvatarUrl(), responseItem.starsCount());
    }

    @NonNull
    public GithubRepoEntity mapToEntity(@NonNull final GithubRepo item) {
        return new GithubRepoEntity(0, item.id(), item.ownerLogin(), item.repoName(), item.description(),
                item.avatarUrl(), item.starsCount());
    }

    @NonNull
    public GithubRepo mapFromEntity(@NonNull final GithubRepoEntity entity) {
        return new GithubRepo(entity.getRepoId(), entity.getOwnerLogin(), entity.getRepoName(), entity.getDescription(),
                entity.getAvatarUrl(), entity.getStarsCount());
    }

    @NonNull
    public GithubRepoDetails mapDetailsToUI(@NonNull final GithubRepoDetailsResponse responseItem) {
        final GithubRepo basicInfo = mapToUIByFields(responseItem.id(), responseItem.owner().login(),
                responseItem.name(), responseItem.description(),
                responseItem.owner().avatarUrl(), responseItem.starsCount());
        final ZonedDateTime zdt = ZonedDateTime.parse(responseItem.creationDate());
        final LocalDateTime ldt = zdt.toLocalDateTime();

        return new GithubRepoDetails(basicInfo, responseItem.repoUrl(), responseItem.forksCount(),
                ldt.toLocalDate().toString(), responseItem.language());
    }

    private GithubRepo mapToUIByFields(final long id, @NonNull final String ownerLogin,
                                       @NonNull final String name, @Nullable final String description,
                                       @NonNull final String avatarUrl, final int starsCount) {
        return new GithubRepo(id, ownerLogin, name,
                !TextUtils.isEmpty(description) ? description : resManager.getString(R.string.repo_description_default_mock),
                avatarUrl, starsCount);
    }
}
