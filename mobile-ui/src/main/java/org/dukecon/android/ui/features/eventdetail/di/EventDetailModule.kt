package org.dukecon.android.ui.features.eventdetail.di

import dagger.Module
import dagger.Provides
import org.dukecon.android.ui.features.speakerdetail.SpeakerNavigator
import org.dukecon.domain.aspects.auth.AuthManager
import org.dukecon.domain.features.eventdetail.GetEventDetailUseCase
import org.dukecon.domain.features.eventdetail.SetFavoriteUseCase
import org.dukecon.domain.features.login.GetTokenFromStorage
import org.dukecon.presentation.feature.eventdetail.EventDetailContract
import org.dukecon.presentation.feature.eventdetail.EventDetailPresenter
import org.dukecon.presentation.mapper.EventMapper
import org.dukecon.presentation.mapper.SpeakerMapper

@Module
class EventDetailModule(val speakerNavigator: SpeakerNavigator) {

    @Provides
    fun provideSpeakerNavigator(): SpeakerNavigator {
        return speakerNavigator
    }

    @Provides
    fun provideEventDetailPresenter(
        getEventDetailUseCase: GetEventDetailUseCase,
        setFavoriteUseCase: SetFavoriteUseCase,
        speakerMapper: SpeakerMapper,
        eventsMapper: EventMapper,
        getTokenFromStorage: GetTokenFromStorage,
        authManager: AuthManager
    ): EventDetailContract.Presenter {
        return EventDetailPresenter(
            getEventDetailUseCase,
            setFavoriteUseCase,
            speakerMapper,
            eventsMapper,
            getTokenFromStorage,
            authManager)
    }
}