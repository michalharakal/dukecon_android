package org.dukecon.domain.repository

import io.reactivex.Completable
import io.reactivex.Single
import org.dukecon.domain.model.Event
import org.dukecon.domain.model.Room
import org.dukecon.domain.model.Speaker
import org.joda.time.DateTime

/**
 * Interface defining methods for how the data layer can pass data to and from the Domain layer.
 * This is to be implemented by the data layer, setting the requirements for the
 * operations that need to be implemented
 */
interface EventRepository {

    fun clearEvents(): Completable

    fun saveEvents(events: List<Event>): Completable

    fun getEvents(day: Int): Single<List<Event>>
    fun getEventDates(): Single<List<DateTime>>

    fun getSpeakers(): Single<List<Speaker>>
    fun saveSpeakers(it: List<Speaker>): Completable

    fun getRooms(): Single<List<Room>>
    fun saveRooms(it: List<Room>): Completable

}