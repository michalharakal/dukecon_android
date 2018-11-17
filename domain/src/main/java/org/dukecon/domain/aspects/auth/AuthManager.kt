package org.dukecon.domain.aspects.auth

interface AuthManager {
    fun login()
    fun exchangeToken(code: String)
}