package org.dukecon.presentation.model

import org.dukecon.domain.model.Favorite
import org.threeten.bp.OffsetDateTime

data class EventView(val id: String,
                     val title: String,
                     val description: String,
                     val startTime: OffsetDateTime,
                     val endTime: OffsetDateTime,
                     val speakers: List<SpeakerView>,
                     val favorite: Favorite,
                     val room: String)