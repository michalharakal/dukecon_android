package org.dukecon.data.source

import io.reactivex.Completable
import io.reactivex.Single
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

    override fun submitFeedback(feedback: FeedbackEntity): Single<Any> {
        return eventRemote.submitFeedback(feedback)
    }

    override fun saveFavorite(favorite: FavoriteEntity): Single<List<FavoriteEntity>> {
        throw UnsupportedOperationException()
    }

    // no call to API yet
    override fun getFavorites(): Single<List<FavoriteEntity>> {
        throw UnsupportedOperationException()
    }

    override fun getSpeaker(id: String): Single<SpeakerEntity> {
        return eventRemote.getSpeaker(id)
    }

    override fun getEvent(id: String): Single<EventEntity> {
        return eventRemote.getEvent(id)
    }

    override fun getRooms(): Single<List<RoomEntity>> {
        return eventRemote.getRooms();
    }

    override fun saveRooms(rooms: List<RoomEntity>): Completable {
        throw UnsupportedOperationException()
    }

    override fun getSpeakers(): Single<List<SpeakerEntity>> {
        return eventRemote.getSpeakers()
    }

    override fun saveSpeakers(speakers: List<SpeakerEntity>): Completable {
        throw UnsupportedOperationException()
    }

    override fun saveEvents(events: List<EventEntity>): Completable {
        throw UnsupportedOperationException()
    }

    override fun getEvents(): Single<List<EventEntity>> {
        return eventRemote.getEvents()
    }

    override fun clearEvents(): Completable {
        throw UnsupportedOperationException()
    }
}