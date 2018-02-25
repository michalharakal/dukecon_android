package org.dukecon.data.model

import org.joda.time.DateTime
import java.util.*

/**
 * Representation for a [EventEntity] fetched from an external layer data source
 */

data class EventEntity(val name: String, val title: String, val abstractText: String, val startTime: DateTime,
                       val endTime: DateTime, val speakerIds: List<String>)

