package org.dukecon.android.ui.injection

import dagger.Module
import dagger.Provides
import org.dukecon.data.EventDataRepository
import org.dukecon.data.mapper.EventMapper
import org.dukecon.data.mapper.RoomMapper
import org.dukecon.data.mapper.SpeakerMapper
import org.dukecon.data.source.EventDataStoreFactory
import org.dukecon.domain.repository.EventRepository


@Module
class DataModule {
    @Provides
    internal fun provideEventRepository(factory: EventDataStoreFactory,
                                        mapper: EventMapper,
                                        speakerMapper: SpeakerMapper,
                                        roomMapper: RoomMapper): EventRepository {
        return EventDataRepository(factory, mapper, speakerMapper, roomMapper)
    }

}