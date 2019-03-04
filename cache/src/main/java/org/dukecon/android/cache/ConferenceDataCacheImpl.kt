package org.dukecon.android.cache

import mu.KotlinLogging
import org.dukecon.android.cache.persistance.ConferenceCacheSerializer
import org.dukecon.data.model.*
import org.dukecon.data.repository.ConferenceDataCache
import org.threeten.bp.OffsetDateTime
import javax.inject.Inject

private val logger = KotlinLogging.logger {}

/**
 * Cached implementation for retrieving and saving Event instances. This class implements the
 * [ConferenceDataCache] from the Data layer as it is that layers responsibility for defining the
 * operations in which data store implementation layers can carry out. Just simple in memory chache
 */
class ConferenceDataCacheImpl @Inject constructor(
        private val conferenceCacheSerializer: ConferenceCacheSerializer,
        private val preferencesHelper: PreferencesHelper
) : ConferenceDataCache {
    override suspend fun getKeycloak(): KeycloakEntity {
        logger.info { "reading getRooms from memory cache" }
        return keycloakEntity
    }

    var cachedRooms: List<RoomEntity> = listOf()
    var cachedEvents: List<EventEntity> = listOf()
    var cacheSpeakers: List<SpeakerEntity> = listOf()
    var cacheFavorites: List<FavoriteEntity> = listOf()
    var keycloakEntity = KeycloakEntity(
            "dukecon-latest",
            "https://keycloak.dukecon.org/auth",
            "none",
            "dukecon",
            "/rest/preferences",
            false
    )

    init {
        if (preferencesHelper.lastCacheTime > 0) {
            logger.info { "cold start, reading data from disc cache" }
            conferenceCacheSerializer.run {
                cachedRooms = readRooms()
                cachedEvents = readEvents()
                cacheSpeakers = readSpeakers()
                cacheFavorites = readFavorites()
                keycloakEntity = readKeyCloack()
            }
        }
    }

    override suspend fun clearEvents() {
        preferencesHelper.lastCacheTime = 0
        cachedEvents = listOf()
        cacheSpeakers = listOf()
        cachedRooms = listOf()
        cacheFavorites = listOf()
    }

    override suspend fun isCached(): Boolean {
        val cached = cachedEvents.isNotEmpty() && cacheSpeakers.isNotEmpty() && cachedRooms.isNotEmpty()
        return cached
    }

    override suspend fun saveRooms(rooms: List<RoomEntity>) {
        cachedRooms = rooms
        conferenceCacheSerializer.writeRooms(cachedRooms)
        preferencesHelper.lastCacheTime = System.currentTimeMillis()
    }

    override suspend fun getRooms(): List<RoomEntity> {
        logger.info { "reading getRooms from memory cache" }
        return cachedRooms
    }


    override suspend fun saveEvents(events: List<EventEntity>) {
        cachedEvents = events
        conferenceCacheSerializer.writeEvents(events)
        preferencesHelper.lastCacheTime = System.currentTimeMillis()
    }

    override suspend fun getEvents(): List<EventEntity> {
        logger.info { "reading getEvents from memory cache" }
        return cachedEvents
    }

    override suspend fun getEvent(id: String): EventEntity {
        logger.info { "reading getEvent with id= ${id} from memory cache" }
        return cachedEvents.find { event -> event.id == id } ?: emptyEntity()
    }

    override suspend fun saveSpeakers(speakers: List<SpeakerEntity>) {
        cacheSpeakers = speakers
        conferenceCacheSerializer.writeSpeakers(speakers)
        preferencesHelper.lastCacheTime = System.currentTimeMillis()
    }

    override suspend fun getSpeakers(): List<SpeakerEntity> {
        return cacheSpeakers
    }

    override suspend fun getSpeaker(id: String): SpeakerEntity {
        logger.info { "reading speaker with id= ${id} from memory cache" }
        return cacheSpeakers.find { speaker -> speaker.id == id } ?: emptySpeakerEntity()
    }

    override suspend fun getFavorites(): List<FavoriteEntity> {
        logger.info { "reading favorites from memory cache" }
        return cacheFavorites
    }

    override suspend fun saveFavorite(favorite: FavoriteEntity): List<FavoriteEntity> {
        val foundFavorite = cacheFavorites.find { it.id.equals(favorite.id) }
        var changed = false
        if (foundFavorite == null) {
            // add
            if (favorite.selected) {
                val newlist: MutableList<FavoriteEntity> = cacheFavorites.toMutableList()
                newlist.add(favorite)
                cacheFavorites = newlist
                changed = true
            }
        } else {
            if (!favorite.selected) {
                val newlist: MutableList<FavoriteEntity> = cacheFavorites.toMutableList()
                for (i in cacheFavorites.indices) {
                    if (cacheFavorites[i].id == favorite.id) {
                        newlist.removeAt(i)
                        break
                    }
                }
                cacheFavorites = newlist
                changed = true
            }
        }
        if (changed) {
            conferenceCacheSerializer.writeFavorites(cacheFavorites)
            preferencesHelper.lastCacheTime = System.currentTimeMillis()
        }
        return cacheFavorites

    }

    private fun emptySpeakerEntity(): SpeakerEntity {
        return SpeakerEntity("", "", "", "", "", "", "")
    }

    private fun emptyEntity(): EventEntity {
        val event = EventEntity("", "", "", OffsetDateTime.now(), OffsetDateTime.now(), listOf(), "")
        return event
    }
}