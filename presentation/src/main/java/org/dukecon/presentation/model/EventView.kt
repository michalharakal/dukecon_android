package org.dukecon.presentation.model

import org.joda.time.DateTime


/**
 * Representation for a [EventView] instance for this layers Model representation
 */
data class EventView(val name: String, val title: String, val startTime: DateTime, val endTime: DateTime,
                     val speakersId: List<String>, val room: String)