package org.dukecon.presentation.mapper

import org.dukecon.domain.model.Event
import org.dukecon.domain.model.Speaker
import org.dukecon.presentation.model.EventView
import org.dukecon.presentation.model.SpeakerView
import javax.inject.Inject

/**
 * Map a [EventView] to and from a [Event] instance when data is moving between
 * this layer and the Domain layer
 */
open class EventMapper @Inject constructor(val speakersMapper: SpeakerMapper) : Mapper<EventView, Event> {

    /**
     * Map a [Event] instance to a [EventView] instance
     */
    override fun mapToView(type: Event): EventView {
        return EventView(type.name, type.title, type.description, type.startTime, type.endTime,
                type.speakerIds.map { speakersMapper.mapToView(it) }, type.room)
    }
}