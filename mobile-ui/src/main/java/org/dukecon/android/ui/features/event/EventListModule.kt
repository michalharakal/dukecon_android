package org.dukecon.android.ui.features.event

import dagger.Module
import dagger.Provides
import org.dukecon.domain.features.event.GetEventDates
import org.dukecon.domain.features.event.GetEvents
import org.dukecon.domain.interactor.SingleUseCase
import org.dukecon.presentation.feature.event.EventDateListContract
import org.dukecon.presentation.feature.event.EventDatePresenter
import org.dukecon.presentation.feature.event.EventListContract
import org.dukecon.presentation.feature.event.EventListPresenter
import org.dukecon.presentation.mapper.EventMapper

@Module
class EventListModule() {



    @Provides
    fun provideSessionDatePresenter(getEventDateUseCase: GetEventDates): EventDateListContract.Presenter {
        return EventDatePresenter(getEventDateUseCase)
    }

    @Provides
    fun sessionListPresenter(getEvents: GetEvents,
                             eventsMapper: EventMapper): EventListContract.Presenter {
        return EventListPresenter(getEvents, eventsMapper)
    }


}