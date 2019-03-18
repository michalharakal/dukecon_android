package org.dukecon.domain.model

import org.threeten.bp.OffsetDateTime

data class Event(val eventId: String,
                 val title: String,
                 val description: String,
                 val startTime: OffsetDateTime,
                 val endTime: OffsetDateTime,
                 val speakers: List<Speaker>,
                 val favorite: Favorite,
                 val room: Location,
                 val track: Track,
                 val audience: Audience,
                 val eventType: EventType,
                 val demo: Boolean,
                 val language: Language,
                 val simultan: Boolean,
                 val veryPopular: Boolean,
                 val fullyBooked: Boolean,
                 val numberOfFavorites: Int)