package org.dukecon.android.ui.configuration

import android.app.Application
import org.dukecon.data.source.ConferenceConfiguration

class ApexConnConfiguration(val application: Application) : ConferenceConfiguration {
    override val supportsFeedback: Boolean
        get() = false // support for feedback for "Apex Connect 2018" Conference is not deployed yet in backend
    override val speakerAvatarUrl: String
        get() = baseUrl + "speaker/images/"
    override val baseUrl: String
        get() = "https://programm.doag.org/apex/2018/rest/"
    override val conferenceId: String
        get() = "apex2018"
}
