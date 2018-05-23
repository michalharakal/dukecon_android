package org.dukecon.presentation.model

import org.dukecon.domain.model.Favorite
import org.joda.time.DateTime


/**
 * Representation for a [EventView] instance for this layers Model representation
 */
data class EventView(val id: String, val title: String, val description: String,
                     val startTime: DateTime, val endTime: DateTime, val speakersId: List<SpeakerView>, val favorite: Favorite,
                     val room: String)