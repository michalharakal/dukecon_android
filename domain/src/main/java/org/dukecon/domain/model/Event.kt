package org.dukecon.domain.model


import org.joda.time.DateTime
import java.util.Date

/**
 * Representation for a [Event]
 */
data class Event(val name: String, val title: String, val avatar: String, val startTime: DateTime, val endTime: DateTime)