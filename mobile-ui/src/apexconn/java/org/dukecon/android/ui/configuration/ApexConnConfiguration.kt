package org.dukecon.android.ui.configuration

import android.app.Application
import org.dukecon.android.ui.app.ConferenceConfiguration

class ApexConnConfiguration(val application: Application) : ConferenceConfiguration {
    override val baseUrl: String
        get() = "https://programm.doag.org/apex/2018/rest/"
    override val conferenceId: String
        get() = "apex2018"
}
