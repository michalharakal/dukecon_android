package org.dukecon.android.ui.features.eventdetail.di

import dagger.Module
import dagger.Provides
import org.dukecon.android.ui.features.login.MainNavigator

@Module
class RedirectUrilModule(val mainNavigator: MainNavigator) {

    @Provides
    fun provideMainNavigator(): MainNavigator {
        return mainNavigator
    }
}