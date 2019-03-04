package org.dukecon.data.repository

import org.dukecon.data.model.*


/**
 * Interface defining methods for the caching of Bufferroos. This is to be implemented by the
 * cache layer, using this interface as a way of communicating.
 */
interface ConferenceDataCache {

    /**
     * Clear all Events from the cache
     */
    suspend fun clearEvents()

    /**
     * Save a given list of EventEntity to the cache
     */
    suspend fun saveEvents(events: List<EventEntity>)

    /**
     * Retrieve a list of Events, from the cache
     */
    suspend fun getEvents(): List<EventEntity>

    suspend fun isCached(): Boolean

    suspend fun getSpeakers(): List<SpeakerEntity>
    suspend fun getSpeaker(id: String): SpeakerEntity

    suspend fun saveSpeakers(speakers: List<SpeakerEntity>)

    suspend fun getRooms(): List<RoomEntity>
    suspend fun saveRooms(rooms: List<RoomEntity>)

    suspend fun getEvent(id: String): EventEntity

    suspend fun getFavorites(): List<FavoriteEntity>
    suspend fun saveFavorite(favorite: FavoriteEntity): List<FavoriteEntity>

    suspend fun getKeycloak(): KeycloakEntity

}