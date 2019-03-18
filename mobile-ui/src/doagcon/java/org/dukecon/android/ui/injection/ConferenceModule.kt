package org.dukecon.android.ui.injection

import android.app.Application
import dagger.Module
import dagger.Provides
import org.dukecon.android.ui.configuration.DoagConfiguration
import org.dukecon.android.ui.configuration.DoagOAuthConfiguration
import org.dukecon.data.source.ConferenceConfiguration
import org.dukecon.data.source.OAuthConfiguration

@Module
open class ConferenceModule {

    @Provides
    fun provideConfiguration(application: Application): ConferenceConfiguration {
        return DoagConfiguration(application)
    }

    @Provides
    fun provideOAuthConfiguration(application: Application): OAuthConfiguration {
        return DoagOAuthConfiguration(application)
    }
}
