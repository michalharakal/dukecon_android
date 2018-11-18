package org.dukecon.android.ui.configuration

import android.app.Application
import org.dukecon.data.source.OAuthConfiguration

class DoagOAuthConfiguration(val application: Application) : OAuthConfiguration {
    override val baseUrl: String
        //get() = "https://keycloak.dukecon.org/auth/realms/dukecon-developer/protocol/openid-connect/"
        get() = "https://programm.doag.org/auth/realms/dukecon-developer/protocol/openid-connect/"
    override val clientId: String
        get() = "dukecon_mobile"
}
