package org.dukecon.remote.mapper

import org.dukecon.android.api.model.Event
import org.dukecon.data.model.EventEntity
import javax.inject.Inject

/**
 * Map a [Event] to and from a [EventEntity] instance when data is moving between
 * this later and the Data layer
 */
open class EventEntityMapper @Inject constructor(): EntityMapper<Event, EventEntity> {

    /**
     * Map an instance of a [Event] to a [EventEntity] model
     */
    override fun mapFromRemote(type: Event): EventEntity {
        return EventEntity(type.id, type.title, type.abstractText, type.start, type.end)
    }

}