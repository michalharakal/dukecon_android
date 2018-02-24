package org.dukecon.data.mapper

import org.dukecon.data.model.EventEntity
import org.dukecon.domain.model.Event
import javax.inject.Inject

/**
 * Map a [EventEntity] to and from a [Event] instance when data is moving between
 * this later and the Domain layer
 */
open class EventMapper @Inject constructor() : Mapper<EventEntity, Event> {

    /**
     * Map a [EventEntity] instance to a [Event] instance
     */
    override fun mapFromEntity(type: EventEntity): Event {
        return Event(type.name, type.title, type.abstractText, type.startTime, type.endTime)
    }

    /**
     * Map a [Event] instance to a [EventEntity] instance
     */
    override fun mapToEntity(type: Event): EventEntity {
        return EventEntity(type.name, type.title, type.avatar, type.startTime, type.endTime)
    }
}