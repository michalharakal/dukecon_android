package org.dukecon.domain.model

import org.threeten.bp.OffsetDateTime

data class Event(val eventId: String,
                 val title: String,
                 val description: String,
                 val startTime: OffsetDateTime,
                 val endTime: OffsetDateTime,
                 val speakers: List<Speaker>,
                 val favorite: Favorite,
                 val room: String)