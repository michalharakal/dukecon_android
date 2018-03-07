package org.dukecon.android.ui.injection

import android.app.Application
import org.dukecon.android.ui.app.ConferenceConfiguration

class JavalandConfiguration(val application: Application) : ConferenceConfiguration {
    override val baseUrl: String
        get() = "https://programm.javaland.eu/2018/rest/" //https://latest.dukecon.org/javaland/2018/rest/") //endpoitUrlProvider.getUrl())
    override val conferenceId: String
        get() = "javaland2018"
}
