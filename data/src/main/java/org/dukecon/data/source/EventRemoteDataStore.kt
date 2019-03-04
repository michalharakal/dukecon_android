package org.dukecon.data.source

import org.dukecon.data.model.*
import org.dukecon.data.repository.EventDataStore
import org.dukecon.data.repository.EventRemote
import javax.inject.Inject


/**
 * Implementation of the [EventDataStore] interface to provide a means of communicating
 * with the remote data source
 */
open class EventRemoteDataStore @Inject constructor(private val eventRemote: EventRemote) :
        EventDataStore {
    override suspend fun getKeycloak(): KeycloakEntity {
        return eventRemote.getKeycloak()
    }

    override suspend fun submitFeedback(feedback: FeedbackEntity): Any {
        return eventRemote.submitFeedback(feedback)
    }

    override suspend fun saveFavorite(favorite: FavoriteEntity): List<FavoriteEntity> {
        throw UnsupportedOperationException()
    }

    // no call to API yet
    override suspend fun getFavorites(): List<FavoriteEntity> {
        throw UnsupportedOperationException()
    }

    override suspend fun getSpeaker(id: String): SpeakerEntity {
        return eventRemote.getSpeaker(id)
    }

    override suspend fun getEvent(id: String): EventEntity {
        return eventRemote.getEvent(id)
    }

    override suspend fun getRooms(): List<RoomEntity> {
        return eventRemote.getRooms()
    }

    override suspend fun saveRooms(rooms: List<RoomEntity>) {
        throw UnsupportedOperationException()
    }

    override suspend fun getSpeakers(): List<SpeakerEntity> {
        return eventRemote.getSpeakers()
    }

    override suspend fun saveSpeakers(speakers: List<SpeakerEntity>) {
        throw UnsupportedOperationException()
    }

    override suspend fun saveEvents(events: List<EventEntity>) {
        throw UnsupportedOperationException()
    }

    override suspend fun getEvents(): List<EventEntity> {
        return eventRemote.getEvents()
    }

    override suspend fun clearEvents() {
        throw UnsupportedOperationException()
    }
}