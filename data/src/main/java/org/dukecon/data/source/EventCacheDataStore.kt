package org.dukecon.data.source

import io.reactivex.Completable
import io.reactivex.Single
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
    override fun submitFeedback(feedback: FeedbackEntity): Single<Any> {
        throw UnsupportedOperationException()
    }

    override fun saveFavorite(favorite: FavoriteEntity): Single<List<FavoriteEntity>> {
        return conferenceDataCache.saveFavorite(favorite)
    }

    override fun getFavorites(): Single<List<FavoriteEntity>> {
        return conferenceDataCache.getFavorites()
    }

    override fun getSpeaker(id: String): Single<SpeakerEntity> {
        return conferenceDataCache.getSpeaker(id)
    }

    override fun getEvent(id: String): Single<EventEntity> {
        return conferenceDataCache.getEvent(id)
    }

    override fun getRooms(): Single<List<RoomEntity>> {
        return conferenceDataCache.getRooms()
    }

    override fun saveRooms(rooms: List<RoomEntity>): Completable {
        return conferenceDataCache.saveRooms(rooms)
    }

    override fun getSpeakers(): Single<List<SpeakerEntity>> {
        return conferenceDataCache.getSpeakers()
    }

    override fun saveSpeakers(speakers: List<SpeakerEntity>): Completable {
        return conferenceDataCache.saveSpeakers(speakers)
    }

    override fun clearEvents(): Completable {
        return conferenceDataCache.clearEvents()
    }

    override fun saveEvents(events: List<EventEntity>): Completable {
        return conferenceDataCache.saveEvents(events)
    }

    override fun getEvents(): Single<List<EventEntity>> {
        return conferenceDataCache.getEvents();
    }
}