package org.dukecon.data.repository

import io.reactivex.Single
import org.dukecon.data.model.*

interface EventRemote {

    fun getEvents(): Single<List<EventEntity>>
    fun getSpeakers(): Single<List<SpeakerEntity>>
    fun getRooms(): Single<List<RoomEntity>>
    fun getEvent(id: String): Single<EventEntity>
    fun getSpeaker(id: String): Single<SpeakerEntity>
    fun submitFeedback(feedback: FeedbackEntity): Single<Any>
}