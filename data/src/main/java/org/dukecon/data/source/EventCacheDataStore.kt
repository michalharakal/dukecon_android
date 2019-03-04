package org.dukecon.data.source

import org.dukecon.data.model.*
import org.dukecon.data.repository.ConferenceDataCache
import org.dukecon.data.repository.EventDataStore
import javax.inject.Inject


/**
 * Implementation of the [EventDataStore] interface to provide a means of communicating
 * with the local data source
 */
open class EventCacheDataStore @Inject constructor(private val conferenceDataCache: ConferenceDataCache) :
        EventDataStore {
    override suspend fun getKeycloak(): KeycloakEntity {
        return conferenceDataCache.getKeycloak()
    }

    override suspend fun submitFeedback(feedback: FeedbackEntity): Any {
        throw UnsupportedOperationException()
    }

    override suspend fun saveFavorite(favorite: FavoriteEntity): List<FavoriteEntity> {
        return conferenceDataCache.saveFavorite(favorite)
    }

    override suspend fun getFavorites(): List<FavoriteEntity> {
        return conferenceDataCache.getFavorites()
    }

    override suspend fun getSpeaker(id: String): SpeakerEntity {
        return conferenceDataCache.getSpeaker(id)
    }

    override suspend fun getEvent(id: String): EventEntity {
        return conferenceDataCache.getEvent(id)
    }

    override suspend fun getRooms(): List<RoomEntity> {
        return conferenceDataCache.getRooms()
    }

    override suspend fun saveRooms(rooms: List<RoomEntity>) {
        return conferenceDataCache.saveRooms(rooms)
    }

    override suspend fun getSpeakers(): List<SpeakerEntity> {
        return conferenceDataCache.getSpeakers()
    }

    override suspend fun saveSpeakers(speakers: List<SpeakerEntity>) {
        return conferenceDataCache.saveSpeakers(speakers)
    }

    override suspend fun clearEvents() {
        return conferenceDataCache.clearEvents()
    }

    override suspend fun saveEvents(events: List<EventEntity>) {
        return conferenceDataCache.saveEvents(events)
    }

    override suspend fun getEvents(): List<EventEntity> {
        return conferenceDataCache.getEvents()
    }
}