package com.example.githubreposbrowser.features.gitreposlist;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "github_repo")
public class GithubRepoEntity {

    @PrimaryKey(autoGenerate = true)
    private long favoriteId;
    @ColumnInfo(name = "repo_id")
    private long repoId;
    @ColumnInfo(name = "owner_login")
    private String ownerLogin;
    @ColumnInfo(name = "repo_name")
    private String repoName;
    @ColumnInfo(name = "description")
    private String description;
    @ColumnInfo(name = "avatar_url")
    private String avatarUrl;
    @ColumnInfo(name = "stars_count")
    private int starsCount;

    public GithubRepoEntity(long favoriteId, long repoId, String ownerLogin, String repoName, String description, String avatarUrl, int starsCount) {
        this.favoriteId = favoriteId;
        this.repoId = repoId;
        this.ownerLogin = ownerLogin;
        this.repoName = repoName;
        this.description = description;
        this.avatarUrl = avatarUrl;
        this.starsCount = starsCount;
    }

    public long getFavoriteId() {
        return favoriteId;
    }

    public void setFavoriteId(long favoriteId) {
        this.favoriteId = favoriteId;
    }

    public long getRepoId() {
        return repoId;
    }

    public void setRepoId(long repoId) {
        this.repoId = repoId;
    }

    public String getOwnerLogin() {
        return ownerLogin;
    }

    public void setOwnerLogin(String ownerLogin) {
        this.ownerLogin = ownerLogin;
    }

    public String getRepoName() {
        return repoName;
    }

    public void setRepoName(String repoName) {
        this.repoName = repoName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public int getStarsCount() {
        return starsCount;
    }

    public void setStarsCount(int starsCount) {
        this.starsCount = starsCount;
    }
}
