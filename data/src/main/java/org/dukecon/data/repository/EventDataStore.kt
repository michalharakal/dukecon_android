package org.dukecon.data.repository

import io.reactivex.Completable
import io.reactivex.Single
import org.dukecon.data.model.EventEntity
import org.dukecon.data.model.RoomEntity
import org.dukecon.data.model.SpeakerEntity

interface EventDataStore {

    fun clearEvents(): Completable

    fun saveEvents(events: List<EventEntity>): Completable

    fun getEvents(): Single<List<EventEntity>>

    fun getSpeakers(): Single<List<SpeakerEntity>>

    fun saveSpeakers(speakers: List<SpeakerEntity>): Completable

    fun getRooms(): Single<List<RoomEntity>>

    fun saveRooms(rooms: List<RoomEntity>): Completable

    fun getEvent(id: String): Single<EventEntity>

}