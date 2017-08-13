package org.dukecon.android.features.sessions.di;

import org.dukecon.android.MainActivity;
import org.dukecon.android.app.di.FeatureScope;

import dagger.Subcomponent;

@FeatureScope
@Subcomponent(
        modules = {SessionsModule.class}
)

public interface SessionsComponent {
    void injects(MainActivity mainActivity);
}
