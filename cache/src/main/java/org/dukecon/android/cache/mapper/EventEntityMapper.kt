package org.dukecon.android.cache.mapper

import org.dukecon.android.cache.model.CachedEvent
import org.dukecon.data.model.EventEntity
import java.util.*
import javax.inject.Inject


/**
 * Map a [CachedEvent] instance to and from a [EventEntity] instance when data is moving between
 * this later and the Data layer
 */
class EventEntityMapper @Inject constructor() : EntityMapper<CachedEvent, EventEntity> {

    /**
     * Map a [EventEntity] instance to a [CachedEvent] instance
     */
    override fun mapToCached(type: EventEntity): CachedEvent {
        return CachedEvent(type.name, type.title, type.avatar, type.startTime, type.endTime)
    }

    /**
     * Map a [CachedEvent] instance to a [EventEntity] instance
     */
    override fun mapFromCached(type: CachedEvent): EventEntity {
        return EventEntity(type.name, type.title, type.avatar, type.startTime, type.endTime)
    }

}