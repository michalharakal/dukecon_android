package org.dukecon.data.repository

import io.reactivex.Completable
import io.reactivex.Single
import org.dukecon.data.model.EventEntity
import org.dukecon.data.model.FavoriteEntity
import org.dukecon.data.model.RoomEntity
import org.dukecon.data.model.SpeakerEntity
import org.dukecon.domain.model.Favorite

interface EventDataStore {

    fun clearEvents(): Completable

    fun getEvents(): Single<List<EventEntity>>
    fun getEvent(id: String): Single<EventEntity>
    fun saveEvents(events: List<EventEntity>): Completable

    fun getSpeakers(): Single<List<SpeakerEntity>>
    fun getSpeaker(id: String): Single<SpeakerEntity>
    fun saveSpeakers(speakers: List<SpeakerEntity>): Completable

    fun getRooms(): Single<List<RoomEntity>>
    fun saveRooms(rooms: List<RoomEntity>): Completable

    fun getFavorites(): Single<List<FavoriteEntity>>
    fun saveFavorite(favorite: FavoriteEntity): Single<List<FavoriteEntity>>

}