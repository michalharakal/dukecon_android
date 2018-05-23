package org.dukecon.data.mapper

import org.dukecon.data.model.EventEntity
import org.dukecon.domain.model.Event
import org.dukecon.domain.model.Favorite
import org.dukecon.domain.model.Speaker
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
        return Event(type.id, type.id, type.title, type.abstractText, type.startTime, type.endTime,
                createSpeakerList(type.speakerIds), Favorite("0", false), type.roomId)
    }

    private fun createSpeakerList(speakerIds: List<String>): List<Speaker> {
        val result: MutableList<Speaker> = mutableListOf()
        speakerIds.forEach {
            run {
                result.add(Speaker(it, "", "", "", "", "", ""))
            }
        }
        return result
    }

    /**
     * Map a [Event] instance to a [EventEntity] instance
     */
    override fun mapToEntity(type: Event): EventEntity {
        return EventEntity(type.name, type.title, type.description, type.startTime, type.endTime,
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