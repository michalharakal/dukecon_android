package org.dukecon.data

import io.reactivex.Completable
import io.reactivex.Single
import org.dukecon.data.mapper.EventMapper
import org.dukecon.data.model.EventEntity
import org.dukecon.data.source.EventDataStoreFactory
import org.dukecon.data.source.EventRemoteDataStore
import org.dukecon.domain.model.Event
import org.dukecon.domain.repository.EventRepository
import javax.inject.Inject


/**
 * Provides an implementation of the [EventRepository] interface for communicating to and from
 * data sources
 */
class EventDataRepository @Inject constructor(private val factory: EventDataStoreFactory,
                                              private val eventMapper: EventMapper) :
        EventRepository {
    override fun getEventDates(): Single<List<String>> {
        val dataStore = factory.retrieveDataStore()
        return dataStore.getEvents()
                .flatMap {
                    if (dataStore is EventRemoteDataStore) {
                        saveEventEntities(it).toSingle { it }
                    } else {
                        Single.just(it)
                    }
                }
                .map { list ->
                    list.map { listItem ->
                        listItem.startTime.toString()
                    }
                }

    }

    override fun clearEvents(): Completable {
        return factory.retrieveCacheDataStore().clearEvents()
    }

    override fun saveEvents(events: List<Event>): Completable {
        val eventEntities = events.map { eventMapper.mapToEntity(it) }
        return saveEventEntities(eventEntities)
    }

    private fun saveEventEntities(events: List<EventEntity>): Completable {
        return factory.retrieveCacheDataStore().saveEvents(events)
    }

    override fun getEvents(): Single<List<Event>> {
        val dataStore = factory.retrieveDataStore()
        return dataStore.getEvents()
                .flatMap {
                    if (dataStore is EventRemoteDataStore) {
                        saveEventEntities(it).toSingle { it }
                    } else {
                        Single.just(it)
                    }
                }
                .map { list ->
                    list.map { listItem ->
                        eventMapper.mapFromEntity(listItem)
                    }
                }
    }

}