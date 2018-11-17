package org.dukecon.remote.oauth

import org.dukecon.api.KeycloakOAuthApi
import org.dukecon.data.model.OAuthToken
import org.dukecon.data.service.OAuthService
import org.dukecon.data.source.OAuthConfiguration
import java.io.IOException
import javax.inject.Inject

class OAuthServiceImpl @Inject constructor(
    private val conferenceApi: KeycloakOAuthApi,
    private val oAuthConfiguration: OAuthConfiguration
) : OAuthService {
    override fun code2token(code: String): OAuthToken {
        val call = conferenceApi.getOpenIdToken(
            oAuthConfiguration.clientId,
            "authorization_code",
            "appdoag://redirect2token",
            code
        )
        try {
            val response = call.execute()
            if (response.isSuccessful) {
                if (response.body() != null) {
                    val token = response.body()
                    if (token != null) {
                        return OAuthToken(
                            token.accessToken,
                            token.refreshToken, true
                        )
                    }
                }
            }
        } catch (e: IOException) {

        }
        return OAuthToken("", "", false)
    }
}
