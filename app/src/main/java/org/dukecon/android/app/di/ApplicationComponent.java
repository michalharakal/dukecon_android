package org.dukecon.android.app.di;

import org.dukecon.android.features.sessions.di.SessionsComponent;
import org.dukecon.android.features.sessions.di.SessionsModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(
        modules = {ApplicationModule.class, NetModule.class}
)
public interface ApplicationComponent {
    SessionsComponent plus(SessionsModule sessionsModule);
}