package org.dukecon.android.features.sessions.di;

import org.dukecon.android.api.ConferencesApi;
import org.dukecon.android.app.UseCaseThreadPoolHandler;
import org.dukecon.android.app.configuration.ConfigurationsProvider;
import org.dukecon.android.app.di.FeatureScope;
import org.dukecon.android.features.sessions.SessionsContract;
import org.dukecon.android.features.sessions.data.ConferenceRepository;
import org.dukecon.android.features.sessions.SessionsPresenter;
import org.dukecon.android.features.sessions.domain.data.SessionsDataSource;
import org.dukecon.android.features.sessions.domain.usecase.GetSessions;

import dagger.Lazy;
import dagger.Module;
import dagger.Provides;

@Module
public class SessionsModule {

    private final SessionsContract.View view;

    public SessionsModule(SessionsContract.View view) {
        this.view = view;
    }

    @Provides
    @FeatureScope
    SessionsDataSource provideCmlRespotiroy(Lazy<ConferencesApi> defaultApi,
                                            Lazy<ConfigurationsProvider> configurationsProvider) {
        return new ConferenceRepository(defaultApi, configurationsProvider);
    }

    @Provides
    @FeatureScope
    SessionsContract.Presenter provideHomePresenter(SessionsDataSource repository) {
        return new SessionsPresenter(UseCaseThreadPoolHandler.getInstance(), view, new GetSessions(repository));
    }
}
