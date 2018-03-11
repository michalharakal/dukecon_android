package org.dukecon.data.repository

import io.reactivex.Single
import org.dukecon.data.model.EventEntity
import org.dukecon.data.model.FavoriteEntity
import org.dukecon.data.model.RoomEntity
import org.dukecon.data.model.SpeakerEntity

interface EventRemote {

    fun getEvents(): Single<List<EventEntity>>
    fun getSpeakers(): Single<List<SpeakerEntity>>
    fun getRooms(): Single<List<RoomEntity>>
    fun getEvent(id: String): Single<EventEntity>
    fun getSpeaker(id: String): Single<SpeakerEntity>
}