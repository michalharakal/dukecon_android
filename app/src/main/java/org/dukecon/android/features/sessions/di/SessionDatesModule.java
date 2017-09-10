package org.dukecon.android.features.sessions.di;

import org.dukecon.android.app.UseCaseThreadPoolHandler;
import org.dukecon.android.app.di.FeatureScope;
import org.dukecon.android.features.sessions.SessionDateListContract;
import org.dukecon.android.features.sessions.SessionDatesPresenter;
import org.dukecon.android.features.sessions.SessionsDateView;
import org.dukecon.android.features.sessions.domain.data.SessionsDataSource;
import org.dukecon.android.features.sessions.domain.usecase.GetSessionDates;

import dagger.Module;
import dagger.Provides;

@Module
public class SessionDatesModule {
    private final SessionDateListContract.View view;

    public SessionDatesModule(SessionsDateView sessionsDateView) {
        view = sessionsDateView;
    }

    @Provides
    @FeatureScope
    SessionDateListContract.Presenter provideHomePresenter(SessionsDataSource repository) {
        return new SessionDatesPresenter(UseCaseThreadPoolHandler.getInstance(), view, new GetSessionDates
                (repository));
    }
}
