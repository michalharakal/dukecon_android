package org.dukecon.data.source

import com.sun.org.apache.xpath.internal.operations.Bool

interface ConferenceConfiguration {
    val baseUrl: String
    val conferenceId: String
    val speakerAvatarUrl: String
    val supportsFeedback: Boolean
}