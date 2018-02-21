package org.dukecon.domain.repository

import io.reactivex.Completable
import io.reactivex.Single
import org.dukecon.domain.model.Event

/**
 * Interface defining methods for how the data layer can pass data to and from the Domain layer.
 * This is to be implemented by the data layer, setting the requirements for the
 * operations that need to be implemented
 */
interface EventRepository {

    fun clearEvents(): Completable

    fun saveEvents(events: List<Event>): Completable

    fun getEvents(): Single<List<Event>>
    fun getEventDates(): Single<List<String>>

}