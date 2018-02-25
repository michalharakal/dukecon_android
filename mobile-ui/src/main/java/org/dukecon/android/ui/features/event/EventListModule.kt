package org.dukecon.android.ui.features.event

import dagger.Module
import dagger.Provides
import org.dukecon.domain.features.event.GetEventDates
import org.dukecon.domain.features.event.GetEvents
import org.dukecon.domain.features.event.GetSpeakers
import org.dukecon.presentation.feature.event.EventDateListContract
import org.dukecon.presentation.feature.event.EventDatePresenter
import org.dukecon.presentation.feature.event.EventListContract
import org.dukecon.presentation.feature.event.EventListPresenter
import org.dukecon.presentation.mapper.EventMapper
import org.dukecon.presentation.mapper.SpeakerMapper

@Module
class EventListModule() {


    @Provides
    fun provideSessionDatePresenter(getEventDateUseCase: GetEventDates): EventDateListContract.Presenter {
        return EventDatePresenter(getEventDateUseCase)
    }

    @Provides
    fun sessionListPresenter(getEvents: GetEvents, getSpeakers: GetSpeakers,
                             eventsMapper: EventMapper,
                             speakerMapper: SpeakerMapper): EventListContract.Presenter {
        return EventListPresenter(getEvents, getSpeakers, eventsMapper, speakerMapper)
    }


}