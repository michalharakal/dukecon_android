package org.dukecon.android.ui.injection

import dagger.Module
import dagger.Provides
import org.dukecon.data.mapper.*
import org.dukecon.data.repository.DukeconDataRepository
import org.dukecon.data.source.EventDataStoreFactory
import org.dukecon.domain.repository.ConferenceRepository
import javax.inject.Singleton

@Module
class DataModule {
    @Provides
    @Singleton
    internal fun provideEventRepository(
        factory: EventDataStoreFactory,
        mapper: EventMapper,
        speakerMapper: SpeakerMapper,
        roomMapper: RoomMapper,
        feedbackMapper: FeedbackMapper,
        favoriteMapper: FavoriteMapper
    ): ConferenceRepository {
        return DukeconDataRepository(factory, mapper, speakerMapper, roomMapper, feedbackMapper, favoriteMapper)
    }
}