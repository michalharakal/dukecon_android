package org.dukecon.data.mapper

import org.dukecon.data.model.EventEntity
import org.dukecon.domain.model.Event
import org.dukecon.domain.model.Favorite
import org.dukecon.domain.model.Room
import org.dukecon.domain.model.Speaker
import javax.inject.Inject

/**
 * Map a [EventEntity] to and from a [Event] instance when data is moving between
 * this later and the Domain layer
 */
open class EventMapper @Inject constructor() {

    fun mapFromEntity(type: EventEntity, speakers: List<Speaker>, rooms: List<Room>, favorites: List<Favorite>): Event {
        return Event(
                type.id,
                type.title,
                type.abstractText,
                type.startTime,
                type.endTime,
                createSpeakerList(type.speakerIds, speakers),
                mapFavorite(type.id, favorites),
                mapRoomId(type.roomId, rooms))
    }

    private fun mapFavorite(id: String, favorites: List<Favorite>): Favorite {
        return favorites.firstOrNull { it.id == id } ?: Favorite("0", false)
    }

    private fun mapRoomId(roomId: String, rooms: List<Room>): String {
        return rooms.find { it.id == roomId }?.name ?: ""
    }

    private fun createSpeakerList(speakerIds: List<String>, speakers: List<Speaker>): List<Speaker> {
        val result: MutableList<Speaker> = mutableListOf()
        speakerIds.forEach {
            val speaker = speakers.find { speaker -> it == speaker.id }
            if (speaker != null) {
                result.add(
                        Speaker(it,
                                name = speaker.name,
                                title = speaker.title,
                                twitter = speaker.twitter,
                                bio = speaker.bio,
                                website = speaker.website,
                                avatar = speaker.avatar
                        )
                )
            }
        }
        return result
    }

    /**
     * Map a [Event] instance to a [EventEntity] instance
     */
    fun mapToEntity(type: Event): EventEntity {
        return EventEntity(type.eventId, type.title, type.description, type.startTime, type.endTime,
                createIdsList(type.speakers), type.room)
    }

    private fun createIdsList(speakerIds: List<Speaker>): List<String> {
        val result: MutableList<String> = mutableListOf()
        speakerIds.forEach {
            run {
                result.add(it.id)
            }
        }
        return result
    }
}