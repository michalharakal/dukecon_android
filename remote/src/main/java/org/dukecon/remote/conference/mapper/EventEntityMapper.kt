package org.dukecon.remote.conference.mapper

import org.dukecon.android.api.model.Event
import org.dukecon.data.ext.toOffsetDateTime
import org.dukecon.data.model.EventEntity
import javax.inject.Inject

open class EventEntityMapper @Inject constructor() : EntityMapper<Event, EventEntity> {

    override fun mapFromRemote(type: org.dukecon.android.api.model.Event): EventEntity {
        return EventEntity(
                type.id,
                type.title,
                type.abstractText ?: "",
                type.start.toOffsetDateTime(),
                type.end.toOffsetDateTime(),
                type.speakerIds,
                type.locationId)
    }
}