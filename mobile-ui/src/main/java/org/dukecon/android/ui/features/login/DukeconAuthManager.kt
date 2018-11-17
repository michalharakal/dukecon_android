package org.dukecon.android.ui.features.login

import android.content.Context
import android.content.SharedPreferences
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.net.toUri
import org.dukecon.data.service.OAuthService
import org.dukecon.domain.aspects.auth.AuthManager
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DukeconAuthManager @Inject constructor(private val context: Context, private val oAuthService: OAuthService) :
    AuthManager {

    companion object {
        private val PREF_BUFFER_PACKAGE_NAME = "org.dukecon.ouath"
        private val PREF_KEY_ACCESS_TOKEN = "access_token"
        private val PREF_KEY_REFRESH_TOKEN = "refresh_token"
    }

    private val oauthTokenPref: SharedPreferences

    init {
        oauthTokenPref = context.getSharedPreferences(PREF_BUFFER_PACKAGE_NAME, Context.MODE_PRIVATE)
    }

    override fun exchangeToken(code: String) {
        val token = oAuthService.code2token(code)
        if (token.valid) {
            oauthTokenPref.edit().putString(PREF_KEY_ACCESS_TOKEN, token.accessToken).apply()
            oauthTokenPref.edit().putString(PREF_KEY_REFRESH_TOKEN, token.refreshToken).apply()
        }
    }

    override fun login() {
        val intent = CustomTabsIntent.Builder().build()
        intent.launchUrl(
            context,
            "https://keycloak.dukecon.org/auth/realms/dukecon-developer/protocol/openid-connect/auth?client_id=dukecon_mobile&redirect_uri=appdoag://redirect2token&response_type=code&scope=openid%20offline_access".toUri()
        )
    }
}
