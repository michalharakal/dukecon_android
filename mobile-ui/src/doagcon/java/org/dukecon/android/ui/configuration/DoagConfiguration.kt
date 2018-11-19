package org.dukecon.android.ui.configuration

import android.app.Application
import org.dukecon.data.source.ConferenceConfiguration

class DoagConfiguration(val application: Application) : ConferenceConfiguration {
    override val supportsFeedback: Boolean
        get() = true // support for feedback for "Apex Connect 2018" Conference is not deployed yet in backend
    override val speakerAvatarUrl: String
        get() = baseUrl + "speaker/images/"
    override val baseUrl: String
        get() = "https://programm.doag.org/doag/2018/rest/"
    override val conferenceId: String
        get() = "doag2018"
}
