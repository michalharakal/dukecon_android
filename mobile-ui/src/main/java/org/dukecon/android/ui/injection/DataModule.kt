package org.dukecon.android.ui.injection

import dagger.Module
import dagger.Provides
import org.dukecon.data.mapper.*
import org.dukecon.data.repository.ConferenceDataCache
import org.dukecon.data.repository.DukeconDataRepository
import org.dukecon.data.source.EventDataStoreFactory
import org.dukecon.domain.features.networking.NetworkUtils
import org.dukecon.domain.repository.ConferenceRepository
import javax.inject.Singleton

@Module
class DataModule {
    @Provides
    @Singleton
    internal fun provideEventRepository(
        cache: ConferenceDataCache,
        networkUtils: NetworkUtils,
        factory: EventDataStoreFactory,
        mapper: EventMapper,
        speakerMapper: SpeakerMapper,
        roomMapper: RoomMapper,
        feedbackMapper: FeedbackMapper,
        favoriteMapper: FavoriteMapper,
        keycloakMapper: KeycloakMapper
    ): ConferenceRepository {
        return DukeconDataRepository(
            cache,
            networkUtils,
            factory,
            mapper,
            speakerMapper,
            keycloakMapper,
            roomMapper,
            feedbackMapper,
            favoriteMapper
        )
    }
}