package com.example.githubreposbrowser.di.component;

import com.example.githubreposbrowser.base.App;
import com.example.githubreposbrowser.di.module.AppModule;
import com.example.githubreposbrowser.di.module.DbModule;
import com.example.githubreposbrowser.features.gitreposlist.allrepos.di.RepoListFrmComponent;
import com.example.githubreposbrowser.features.gitreposlist.details.di.GithubRepoDetailsDialogComponent;
import com.example.githubreposbrowser.features.gitreposlist.favorites.FavoriteReposFrmComponent;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import io.reactivex.rxjava3.annotations.NonNull;

@Singleton
@Component(modules = {AppModule.class, DbModule.class})
public interface AppComponent {

    @Component.Factory
    interface Factory {
        AppComponent create(@BindsInstance App app);
    }

    @NonNull
    RepoListFrmComponent.Factory plusRepoListFrm();

    @NonNull
    FavoriteReposFrmComponent.Factory plusFavoriteReposFrm();

    @NonNull
    GithubRepoDetailsDialogComponent.Factory plusRepoDetailsDialog();
}
