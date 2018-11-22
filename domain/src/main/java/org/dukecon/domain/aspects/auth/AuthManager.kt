package org.dukecon.domain.aspects.auth

import org.dukecon.domain.model.OAuthToken

interface AuthManager {
    fun login(activity: Object)
    fun exchangeToken(code: String)
    fun hasSession(token: OAuthToken): Boolean
}