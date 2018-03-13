package org.dukecon.android.ui.injection

import android.app.Application
import dagger.Module
import dagger.Provides
import org.dukecon.android.ui.app.ConferenceConfiguration
import org.dukecon.android.ui.configuration.JavalandConfiguration

@Module
open class ConferenceModule {

    @Provides
    fun provideConfiguration(application: Application): ConferenceConfiguration {
        return JavalandConfiguration(application)
    }
}

