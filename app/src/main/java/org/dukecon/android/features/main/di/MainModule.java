package org.dukecon.android.features.main.di;

import org.dukecon.android.api.ConferencesApi;
import org.dukecon.android.app.configuration.ConfigurationsProvider;
import org.dukecon.android.app.di.ActivityScope;
import org.dukecon.android.app.di.FeatureScope;
import org.dukecon.android.features.sessions.SessionNavigator;
import org.dukecon.android.features.sessions.data.ConferenceRepository;
import org.dukecon.android.features.sessions.domain.data.SessionsDataSource;

import dagger.Lazy;
import dagger.Module;
import dagger.Provides;

@Module
public class MainModule {

    private final SessionNavigator sessionNavigator;

    public MainModule(SessionNavigator sessionNavigator) {
        this.sessionNavigator = sessionNavigator;
    }

    @Provides
    @ActivityScope
    SessionsDataSource provideCmlRespotiroy(Lazy<ConferencesApi> defaultApi,
                                            Lazy<ConfigurationsProvider> configurationsProvider) {
        return new ConferenceRepository(defaultApi, configurationsProvider);
    }

    @Provides
    @ActivityScope
    SessionNavigator sessionNavigator() {
        return sessionNavigator;
    }
}
