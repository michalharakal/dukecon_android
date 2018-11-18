package org.dukecon.android.ui.features.login

import android.content.Context
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.net.toUri
import org.dukecon.data.service.OAuthService
import org.dukecon.domain.aspects.auth.AuthManager
import org.dukecon.domain.features.oauth.TokensStorage
import org.dukecon.remote.oauth.mapper.OAuthTokenMapper
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DukeconAuthManager @Inject constructor(
    private val context: Context,
    private val oAuthService: OAuthService,
    private val tokensStorage: TokensStorage,
    private val oAuthTokenMapper: OAuthTokenMapper
) : AuthManager {

    override fun exchangeToken(code: String) {
        val token = oAuthService.code2token(code)
        tokensStorage.setToken(oAuthTokenMapper.map(token))
    }

    override fun login() {
        val intent = CustomTabsIntent.Builder().build()
        intent.launchUrl(
            context,
            "https://keycloak.dukecon.org/auth/realms/dukecon-developer/protocol/openid-connect/auth?client_id=dukecon_mobile&redirect_uri=appdoag://redirect2token&response_type=code&scope=openid%20offline_access".toUri()
        )
    }
}
