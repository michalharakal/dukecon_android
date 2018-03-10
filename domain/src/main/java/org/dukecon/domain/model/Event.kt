package org.dukecon.domain.model


import org.joda.time.DateTime

/**
 * Representation for a [Event]
 */
data class Event(val name: String, val title: String, val description: String, val startTime: DateTime,
                 val endTime: DateTime, val speakers: List<Speaker>, val favorite: Favorite, val room: String)