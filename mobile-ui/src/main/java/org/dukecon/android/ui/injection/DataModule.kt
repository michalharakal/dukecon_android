package org.dukecon.android.ui.injection

import dagger.Module
import dagger.Provides
import org.dukecon.data.mapper.EventMapper
import org.dukecon.data.mapper.FavoriteMapper
import org.dukecon.data.mapper.RoomMapper
import org.dukecon.data.mapper.SpeakerMapper
import org.dukecon.data.repository.DukeconDataRepository
import org.dukecon.data.source.EventDataStoreFactory
import org.dukecon.domain.repository.ConferenceRepository


@Module
class DataModule {
    @Provides
    internal fun provideEventRepository(factory: EventDataStoreFactory,
                                        mapper: EventMapper,
                                        speakerMapper: SpeakerMapper,
                                        roomMapper: RoomMapper,
                                        favoriteMapper: FavoriteMapper): ConferenceRepository {
        return DukeconDataRepository(factory, mapper, speakerMapper, roomMapper, favoriteMapper)
    }

}