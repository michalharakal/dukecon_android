package org.dukecon.android.features.sessions.di;

import org.dukecon.android.app.di.FeatureScope;
import org.dukecon.android.features.sessions.SessionListView;

import dagger.Subcomponent;

@FeatureScope
@Subcomponent(
        modules = {SessionsListModule.class}
)

public interface SessionListComponent {
    void injects(SessionListView sessionListView);
}
