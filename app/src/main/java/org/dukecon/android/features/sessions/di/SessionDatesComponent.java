package org.dukecon.android.features.sessions.di;

import org.dukecon.android.app.di.FeatureScope;
import org.dukecon.android.features.sessions.SessionsDateView;

import dagger.Subcomponent;

@FeatureScope
@Subcomponent(
        modules = {SessionDatesModule.class}
)

public interface SessionDatesComponent {
    void injects(SessionsDateView sessionsDateView);
}
