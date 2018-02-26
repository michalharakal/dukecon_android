package org.dukecon.android.cache

import io.reactivex.Completable
import io.reactivex.Single
import org.dukecon.data.model.EventEntity
import org.dukecon.data.model.RoomEntity
import org.dukecon.data.model.SpeakerEntity
import org.dukecon.data.repository.EventCache
import javax.inject.Inject

/**
 * Cached implementation for retrieving and saving Event instances. This class implements the
 * [EventCache] from the Data layer as it is that layers responsibility for defining the
 * operations in which data store implementation layers can carry out. Just simple in memory chache
 */
class EventCacheImpl @Inject constructor() :
        EventCache {

    var cachedRooms: List<RoomEntity> = listOf()
    var cachedEvents: List<EventEntity> = listOf()
    var cacheSpeakers: List<SpeakerEntity> = listOf()
    private val EXPIRATION_TIME = (60 * 10 * 1000).toLong()

    override fun saveRooms(rooms: List<RoomEntity>): Completable {
        cachedRooms = rooms
        lastCache = System.currentTimeMillis()
        return Completable.complete()
    }

    override fun getRooms(): Single<List<RoomEntity>> {
        return Single.create({ s ->
            s.onSuccess(
                    cachedRooms
            )
        })
    }


    override fun clearEvents(): Completable {
        lastCache = 0
        return Completable.complete()
    }

    override fun saveEvents(events: List<EventEntity>): Completable {
        cachedEvents = events
        lastCache = System.currentTimeMillis()
        return Completable.complete()
    }

    override fun getSpeakers(): Single<List<SpeakerEntity>> {
        return Single.create({ s ->
            s.onSuccess(
                    cacheSpeakers
            )
        })
    }

    override fun saveSpeakers(speakers: List<SpeakerEntity>): Completable {
        cacheSpeakers = speakers
        lastCache = System.currentTimeMillis()
        return Completable.complete()
    }


    override fun getEvents(): Single<List<EventEntity>> {
        return Single.create({ s ->
            s.onSuccess(
                    cachedEvents
            )
        })
    }

    override fun isCached(): Boolean {
        return cachedEvents.isNotEmpty() && cacheSpeakers.isNotEmpty() && cachedRooms.isNotEmpty()
    }

    private var lastCache: Long = 0

    override fun setLastCacheTime(lastCache: Long) {
        this.lastCache = lastCache
    }

    override fun isExpired(): Boolean {
        val currentTime = System.currentTimeMillis()
        val lastUpdateTime = lastCache
        return currentTime - lastUpdateTime > EXPIRATION_TIME
    }
}