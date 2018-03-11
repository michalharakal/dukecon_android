package org.dukecon.data.repository

import io.reactivex.Completable
import io.reactivex.Single
import org.dukecon.data.model.EventEntity
import org.dukecon.data.model.FavoriteEntity
import org.dukecon.data.model.RoomEntity
import org.dukecon.data.model.SpeakerEntity


/**
 * Interface defining methods for the caching of Bufferroos. This is to be implemented by the
 * cache layer, using this interface as a way of communicating.
 */
interface EventCache {

    /**
     * Clear all Events from the cache
     */
    fun clearEvents(): Completable

    /**
     * Save a given list of EventEntity to the cache
     */
    fun saveEvents(events: List<EventEntity>): Completable

    /**
     * Retrieve a list of Events, from the cache
     */
    fun getEvents(): Single<List<EventEntity>>

    fun isCached(): Boolean

    fun setLastCacheTime(lastCache: Long)

    fun isExpired(): Boolean

    fun getSpeakers(): Single<List<SpeakerEntity>>
    fun getSpeaker(id: String): Single<SpeakerEntity>

    fun saveSpeakers(speakers: List<SpeakerEntity>): Completable

    fun getRooms(): Single<List<RoomEntity>>
    fun saveRooms(rooms: List<RoomEntity>): Completable

    fun getEvent(id: String): Single<EventEntity>

    fun getFavorites(): Single<List<FavoriteEntity>>
    fun saveFavorite(favorite: FavoriteEntity): Single<List<FavoriteEntity>>

}