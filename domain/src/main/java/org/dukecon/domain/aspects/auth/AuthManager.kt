package org.dukecon.domain.aspects.auth

interface AuthManager {
    fun login(activity: Object)
    fun exchangeToken(code: String)
}