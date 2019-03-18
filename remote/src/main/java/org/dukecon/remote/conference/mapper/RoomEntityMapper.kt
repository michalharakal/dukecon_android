package org.dukecon.remote.conference.mapper

import org.dukecon.data.model.RoomEntity
import javax.inject.Inject

class RoomEntityMapper @Inject constructor() : EntityMapper<org.dukecon.android.api.model.Location, RoomEntity> {

    override fun mapFromRemote(type: org.dukecon.android.api.model.Location): RoomEntity {
        return RoomEntity(type.id, type.names["de"] ?: "")
    }
}