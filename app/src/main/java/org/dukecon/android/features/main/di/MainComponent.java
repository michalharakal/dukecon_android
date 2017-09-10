package org.dukecon.android.features.main.di;

import org.dukecon.android.app.di.ActivityScope;
import org.dukecon.android.features.sessions.di.SessionDatesComponent;
import org.dukecon.android.features.sessions.di.SessionDatesModule;
import org.dukecon.android.features.sessions.di.SessionListComponent;
import org.dukecon.android.features.sessions.di.SessionsListModule;

import dagger.Subcomponent;

@ActivityScope
@Subcomponent(
        modules = {MainModule.class}
)
public interface MainComponent {

    SessionDatesComponent plus(SessionDatesModule loginModule);

    SessionListComponent plus(SessionsListModule sessionsListModule);
}
