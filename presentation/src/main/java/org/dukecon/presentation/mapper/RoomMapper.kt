package org.dukecon.presentation.mapper

import org.dukecon.domain.model.Room
import org.dukecon.presentation.model.RoomView
import javax.inject.Inject

/**
 * Map a [RoomView] to and from a [Room] instance when data is moving between
 * this layer and the Domain layer
 */
open class RoomMapper @Inject constructor() : Mapper<RoomView, Room> {

    /**
     * Map a [Room] instance to a [RoomView] instance
     */
    override fun mapToView(type: Room): RoomView {
        return RoomView(type.id, type.name)
    }
}