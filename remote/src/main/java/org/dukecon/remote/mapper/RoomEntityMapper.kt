package org.dukecon.remote.mapper

import org.dukecon.android.api.model.Event
import org.dukecon.data.model.EventEntity
import org.dukecon.data.model.RoomEntity
import org.dukecon.domain.model.Room
import javax.inject.Inject

/**
 * Map a [Event] to and from a [EventEntity] instance when data is moving between
 * this later and the Data layer
 */
open class RoomEntityMapper @Inject constructor(): EntityMapper<org.dukecon.android.api.model.Location, RoomEntity> {

    /**
     * Map an instance of a [org.dukecon.android.api.model.Event] to a [EventEntity] model
     */
    override fun mapFromRemote(type: org.dukecon.android.api.model.Location): RoomEntity {
        return RoomEntity(type.id, type.names.get("de")  ?: "")
    }
}