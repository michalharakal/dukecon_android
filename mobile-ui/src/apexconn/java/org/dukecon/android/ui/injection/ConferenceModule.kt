package org.dukecon.android.ui.injection

import android.app.Application
import dagger.Module
import dagger.Provides
import org.dukecon.android.ui.configuration.ApexConnConfiguration
import org.dukecon.data.source.ConferenceConfiguration

@Module
open class ConferenceModule {

    @Provides
    fun provideConfiguration(application: Application): ConferenceConfiguration {
        return ApexConnConfiguration(application)
    }
}
