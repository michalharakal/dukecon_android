package org.dukecon.domain.model

import org.joda.time.DateTime

data class OAuthToken(val accessToken: String, val refreshToken: String, val expiresAt: Long)