package org.dukecon.android.cache

import io.reactivex.Completable
import io.reactivex.Single
import mu.KotlinLogging
import org.dukecon.android.cache.persistance.ConferenceCacheSerializer
import org.dukecon.data.model.EventEntity
import org.dukecon.data.model.FavoriteEntity
import org.dukecon.data.model.RoomEntity
import org.dukecon.data.model.SpeakerEntity
import org.dukecon.data.repository.EventCache
import org.joda.time.DateTime
import javax.inject.Inject

private val logger = KotlinLogging.logger {}

/**
 * Cached implementation for retrieving and saving Event instances. This class implements the
 * [EventCache] from the Data layer as it is that layers responsibility for defining the
 * operations in which data store implementation layers can carry out. Just simple in memory chache
 */
class EventCacheImpl @Inject constructor(val conferenceCacheSerializer: ConferenceCacheSerializer, val preferencesHelper: PreferencesHelper) :
        EventCache {

    var cachedRooms: List<RoomEntity> = listOf()
    var cachedEvents: List<EventEntity> = listOf()
    var cacheSpeakers: List<SpeakerEntity> = listOf()
    var cacheFavorites: List<FavoriteEntity> = listOf()

    // 1 hour cache
    private val EXPIRATION_TIME = (60 * 60 * 1000).toLong()

    init {
        if (preferencesHelper.lastCacheTime > 0) {
            logger.info { "cold start, reading data from disc cache" }
            conferenceCacheSerializer.let {
                cachedRooms = it.readRooms()
                cachedEvents = it.readEvents()
                cacheSpeakers = it.readSpeakers()
                cacheFavorites = it.readFavorites()
            }
        }
    }

    override fun clearEvents(): Completable {
        preferencesHelper.lastCacheTime = 0
        cachedEvents = listOf()
        cacheSpeakers = listOf()
        cachedRooms = listOf()
        cacheFavorites = listOf()
        return Completable.complete()
    }

    override fun isCached(): Boolean {
        return cachedEvents.isNotEmpty() && cacheSpeakers.isNotEmpty() && cachedRooms.isNotEmpty()
    }

    override fun setLastCacheTime(lastCache: Long) {
        preferencesHelper.lastCacheTime = System.currentTimeMillis()
    }

    override fun isExpired(): Boolean {
        val currentTime = System.currentTimeMillis()
        val lastUpdateTime = preferencesHelper.lastCacheTime

        return currentTime - lastUpdateTime > EXPIRATION_TIME
    }

    override fun saveRooms(rooms: List<RoomEntity>): Completable {
        cachedRooms = rooms
        conferenceCacheSerializer.writeRooms(cachedRooms)
        preferencesHelper.lastCacheTime = System.currentTimeMillis()
        return Completable.complete()
    }

    override fun getRooms(): Single<List<RoomEntity>> {
        logger.info { "reading getRooms from memory cache" }
        return Single.create({ s ->
            s.onSuccess(
                    cachedRooms
            )
        })
    }

    override fun saveEvents(events: List<EventEntity>): Completable {
        cachedEvents = events
        conferenceCacheSerializer.writeEvents(events)
        preferencesHelper.lastCacheTime = System.currentTimeMillis()
        return Completable.complete()
    }

    override fun getEvents(): Single<List<EventEntity>> {
        logger.info { "reading getEvents from memory cache" }
        return Single.create({ s ->
            s.onSuccess(
                    cachedEvents
            )
        })
    }

    override fun getEvent(id: String): Single<EventEntity> {
        logger.info { "reading getEvent with id= ${id} from memory cache" }
        return Single.create({ s ->
            val found = cachedEvents.find { event ->
                event.id.equals(id)
            } ?: emptyEntity()
            s.onSuccess(found)
        })
    }

    override fun saveSpeakers(speakers: List<SpeakerEntity>): Completable {
        cacheSpeakers = speakers
        conferenceCacheSerializer.writeSpeakers(speakers)
        preferencesHelper.lastCacheTime = System.currentTimeMillis()
        return Completable.complete()
    }

    override fun getSpeakers(): Single<List<SpeakerEntity>> {
        return Single.create({ s ->
            s.onSuccess(
                    cacheSpeakers
            )
        })
    }

    override fun getSpeaker(id: String): Single<SpeakerEntity> {
        logger.info { "reading speaker with id= ${id} from memory cache" }
        return Single.create({ s ->
            val found = cacheSpeakers.find { speaker ->
                speaker.id.equals(id)
            } ?: emptySpeakerEntity()
            s.onSuccess(found)
        })
    }

    override fun getFavorites(): Single<List<FavoriteEntity>> {
        logger.info { "reading favorites from memory cache" }
        return Single.create({ s ->
            s.onSuccess(
                    cacheFavorites
            )
        })
    }

    override fun saveFavorite(favorite: FavoriteEntity): Single<List<FavoriteEntity>> {
        val foundFavorite = cacheFavorites.find { it.id.equals(favorite.id) }
        var changed = false
        if (foundFavorite == null) {
            // add
            if (favorite.selected) {
                val newlist: MutableList<FavoriteEntity> = cacheFavorites.toMutableList()
                newlist.add(favorite)
                cacheFavorites = newlist.filter { it -> it != null }
                changed = true
            }
        } else {
            if (!favorite.selected) {
                val newlist: MutableList<FavoriteEntity> = cacheFavorites.toMutableList()
                for (i in cacheFavorites.indices) {
                    if (cacheFavorites[i].id.equals(favorite.id)) {
                        newlist.removeAt(i)
                        break
                    }
                }
                cacheFavorites = newlist.filter { it -> it != null }
                changed = true
            }
        }
        if (changed) {
            conferenceCacheSerializer.writeFavorites(cacheFavorites)
            preferencesHelper.lastCacheTime = System.currentTimeMillis()
        }
        return Single.create({ s ->
            s.onSuccess(
                    cacheFavorites
            )
        })
    }


    private fun emptySpeakerEntity(): SpeakerEntity {
        return SpeakerEntity("", "", "", "", "", "", "")
    }


    private fun emptyEntity(): EventEntity {
        val event = EventEntity("", "", "", DateTime(), DateTime(), listOf(), "")
        return event
    }
}