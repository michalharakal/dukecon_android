package org.dukecon.data.source

import io.reactivex.Completable
import io.reactivex.Single
import org.dukecon.data.model.EventEntity
import org.dukecon.data.model.FavoriteEntity
import org.dukecon.data.model.RoomEntity
import org.dukecon.data.model.SpeakerEntity
import org.dukecon.data.repository.EventCache
import org.dukecon.data.repository.EventDataStore
import javax.inject.Inject


/**
 * Implementation of the [EventDataStore] interface to provide a means of communicating
 * with the local data source
 */
open class EventCacheDataStore @Inject constructor(private val eventCache: EventCache) :
        EventDataStore {

    override fun saveFavorite(favorite: FavoriteEntity): Single<List<FavoriteEntity>> {
        return eventCache.saveFavorite(favorite)
    }

    override fun getFavorites(): Single<List<FavoriteEntity>> {
        return eventCache.getFavorites()
    }

    override fun getSpeaker(id: String): Single<SpeakerEntity> {
        return eventCache.getSpeaker(id)
    }

    override fun getEvent(id: String): Single<EventEntity> {
        return eventCache.getEvent(id)
    }

    override fun getRooms(): Single<List<RoomEntity>> {
        return eventCache.getRooms()
    }

    override fun saveRooms(rooms: List<RoomEntity>): Completable {
        return eventCache.saveRooms(rooms)
    }

    override fun getSpeakers(): Single<List<SpeakerEntity>> {
        return eventCache.getSpeakers()
    }

    override fun saveSpeakers(speakers: List<SpeakerEntity>): Completable {
        return eventCache.saveSpeakers(speakers)
    }

    override fun clearEvents(): Completable {
        return eventCache.clearEvents()
    }

    override fun saveEvents(events: List<EventEntity>): Completable {
        return eventCache.saveEvents(events)
    }

    override fun getEvents(): Single<List<EventEntity>> {
        return eventCache.getEvents();
    }
}