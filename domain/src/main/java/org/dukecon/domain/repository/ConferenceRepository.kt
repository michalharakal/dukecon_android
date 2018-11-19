package org.dukecon.domain.repository

import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import org.dukecon.domain.model.*
import org.joda.time.DateTime

/**
 * Interface defining methods for how the data layer can pass data to and from the Domain layer.
 * This is to be implemented by the data layer, setting the requirements for the
 * operations that need to be implemented
 */
interface ConferenceRepository {

    fun clearEvents(): Completable

    fun saveEvents(events: List<Event>): Completable

    fun getEvents(day: Int): Single<List<Event>>
    fun getEventDates(): Single<List<DateTime>>

    fun getSpeakers(): Single<List<Speaker>>
    fun getSpeaker(id: String): Single<Speaker>
    fun saveSpeakers(speakers: List<Speaker>): Completable

    fun getRooms(): Single<List<Room>>
    fun saveRooms(rooms: List<Room>): Completable

    fun getEvent(id: String): Single<Event>

    fun saveFavorite(favorite: Favorite): Single<List<Favorite>>
    fun getFavorites(): Single<List<Favorite>>

    fun getEventChanges():Observable<Change>

    fun submitFeedback(feedback: Feedback): Single<Any>

    fun getKeyCloak():Single<Keycloak>
}