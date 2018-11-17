package org.dukecon.remote.conference.mapper

import org.dukecon.android.api.model.Event
import org.dukecon.data.model.EventEntity
import javax.inject.Inject

/**
 * Map a [Event] to and from a [EventEntity] instance when data is moving between
 * this later and the Data layer
 */
open class EventEntityMapper @Inject constructor(): EntityMapper<Event, EventEntity> {

    /**
     * Map an instance of a [org.dukecon.android.api.model.EventEvent] to a [EventEntity] model
     */
    override fun mapFromRemote(type: org.dukecon.android.api.model.Event): EventEntity {
        return EventEntity(type.id, type.title, type.abstractText ?: "" , type.start, type.end, type.speakerIds, type.locationId)
    }
}