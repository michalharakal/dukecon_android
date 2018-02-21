package org.dukecon.android.ui.injection

import dagger.Module
import dagger.Provides
import org.dukecon.android.ui.injection.scopes.PerApplication
import org.dukecon.data.EventDataRepository
import org.dukecon.data.mapper.EventMapper
import org.dukecon.data.source.EventDataStoreFactory
import org.dukecon.domain.repository.EventRepository


@Module
class DataModule {
    @Provides
    internal fun provideEventRepository(factory: EventDataStoreFactory,
                                        mapper: EventMapper): EventRepository {
        return EventDataRepository(factory, mapper)
    }

}