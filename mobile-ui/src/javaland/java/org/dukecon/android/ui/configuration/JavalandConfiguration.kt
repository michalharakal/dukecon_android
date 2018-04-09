package org.dukecon.android.ui.configuration

import android.app.Application
import org.dukecon.data.source.ConferenceConfiguration

class JavalandConfiguration(val application: Application) : ConferenceConfiguration {
    override val speakerAvatarUrl: String
        get() = baseUrl + "speaker/images/"
    override val baseUrl: String
        get() = "https://programm.javaland.eu/2018/rest/" //https://latest.dukecon.org/javaland/2018/rest/") //endpoitUrlProvider.getUrl())
    override val conferenceId: String
        get() = "javaland2018"
}
