package org.dukecon.android.features.sessions.di;

import org.dukecon.android.app.UseCaseThreadPoolHandler;
import org.dukecon.android.app.di.FeatureScope;
import org.dukecon.android.features.sessions.SessionListContract;
import org.dukecon.android.features.sessions.SessionsPresenter;
import org.dukecon.android.features.sessions.domain.data.SessionsDataSource;
import org.dukecon.android.features.sessions.domain.usecase.GetSessions;

import dagger.Module;
import dagger.Provides;

@Module
public class SessionsListModule {

    private final SessionListContract.View view;

    public SessionsListModule(SessionListContract.View view) {
        this.view = view;
    }

    @Provides
    @FeatureScope
    SessionListContract.Presenter provideHomePresenter(SessionsDataSource repository) {
        return new SessionsPresenter(UseCaseThreadPoolHandler.getInstance(), view, new GetSessions(repository));
    }
}
