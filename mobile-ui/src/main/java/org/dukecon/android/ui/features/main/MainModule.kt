package org.dukecon.android.ui.features.main

import dagger.Module
import dagger.Provides
import org.dukecon.android.ui.features.event.SessionNavigator

@Module
class MainModule(val sessionNavigator: SessionNavigator) {

    @Provides fun sessionNavigator(): SessionNavigator {
        return sessionNavigator
    }
}