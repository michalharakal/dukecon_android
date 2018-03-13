package org.dukecon.android.ui.features.event

import dagger.Module
import dagger.Provides
import org.dukecon.domain.features.event.GetEventDates
import org.dukecon.domain.features.event.GetEvents
import org.dukecon.domain.features.event.GetRooms
import org.dukecon.domain.features.event.GetSpeakers
import org.dukecon.domain.features.time.CurrentTimeProvider
import org.dukecon.domain.repository.ConferenceRepository
import org.dukecon.presentation.feature.event.EventDateListContract
import org.dukecon.presentation.feature.event.EventDatePresenter
import org.dukecon.presentation.feature.event.EventListContract
import org.dukecon.presentation.feature.event.EventListPresenter
import org.dukecon.presentation.mapper.EventMapper
import org.dukecon.presentation.mapper.RoomMapper
import org.dukecon.presentation.mapper.SpeakerMapper

@Module
class EventListModule() {


    @Provides
    fun provideSessionDatePresenter(getEventDateUseCase: GetEventDates): EventDateListContract.Presenter {
        return EventDatePresenter(getEventDateUseCase)
    }

    @Provides
    fun sessionListPresenter(currentTimeProvider: CurrentTimeProvider,
                             conferenceRepository: ConferenceRepository,
                             getEvents: GetEvents,
                             getSpeakers: GetSpeakers,
                             getRooms: GetRooms,
                             eventsMapper: EventMapper,
                             speakerMapper: SpeakerMapper,
                             roomMapper: RoomMapper): EventListContract.Presenter {
        return EventListPresenter(currentTimeProvider, conferenceRepository, getEvents, getSpeakers, getRooms, eventsMapper, speakerMapper, roomMapper)
    }


}