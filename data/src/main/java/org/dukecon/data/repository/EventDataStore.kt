package org.dukecon.data.repository

import org.dukecon.data.model.*

interface EventDataStore {

    suspend fun clearEvents()

    suspend fun getEvents(): List<EventEntity>
    suspend fun getEvent(id: String): EventEntity
    suspend fun saveEvents(events: List<EventEntity>)

    suspend fun getSpeakers(): List<SpeakerEntity>
    suspend fun getSpeaker(id: String): SpeakerEntity
    suspend fun saveSpeakers(speakers: List<SpeakerEntity>)

    suspend fun getRooms(): List<RoomEntity>
    suspend fun saveRooms(rooms: List<RoomEntity>)

    suspend fun getFavorites(): List<FavoriteEntity>
    suspend fun saveFavorite(favorite: FavoriteEntity): List<FavoriteEntity>
    suspend fun submitFeedback(feedback: FeedbackEntity): Any

    suspend fun getKeycloak(): KeycloakEntity
}