package org.dukecon.data.model

import org.threeten.bp.OffsetDateTime

data class EventEntity(val id: String,
                       val title: String,
                       val abstractText: String,
                       val startTime: OffsetDateTime,
                       val endTime: OffsetDateTime,
                       val speakerIds: List<String>,
                       val roomId: String)

