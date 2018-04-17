package org.dukecon.android.ui.configuration

import android.app.Application
import org.dukecon.data.source.ConferenceConfiguration

class ApexConnConfiguration(val application: Application) : ConferenceConfiguration {
    override val speakerAvatarUrl: String
        get() = "https://programm.doag.org/apex/2018/rest/"
    override val baseUrl: String
        get() = "https://programm.doag.org/apex/2018/rest/"
    override val conferenceId: String
        get() = "apex2018"
}
