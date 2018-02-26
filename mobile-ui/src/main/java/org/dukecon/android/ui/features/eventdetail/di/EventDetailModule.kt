package org.dukecon.android.ui.features.eventdetail.di

import dagger.Module
import dagger.Provides
import org.dukecon.domain.features.eventdetail.GetEventDetailUseCase
import org.dukecon.presentation.feature.eventdetail.EventDetailContract
import org.dukecon.presentation.feature.eventdetail.EventDetailPresenter
import org.dukecon.presentation.mapper.EventMapper


@Module
class EventDetailModule() {

    @Provides
    fun provideEventDetailPresenter(
            getEventDetailUseCase: GetEventDetailUseCase,
            eventsMapper: EventMapper): EventDetailContract.Presenter {
        return EventDetailPresenter(getEventDetailUseCase, eventsMapper)
    }
}