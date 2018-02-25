package org.dukecon.data.repository

import io.reactivex.Completable
import io.reactivex.Single
import org.dukecon.data.model.EventEntity
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

    /**
     * Checks if the cache is expired.

     * @return true, the cache is expired, otherwise false.
     */
    fun isExpired(): Boolean

    fun getSpeakers(): Single<List<SpeakerEntity>>

    fun saveSpeakers(speakers: List<SpeakerEntity>): Completable

}