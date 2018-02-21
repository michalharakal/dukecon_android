package org.dukecon.data.repository

import io.reactivex.Completable
import io.reactivex.Single
import org.dukecon.data.model.EventEntity

interface EventDataStore {

    fun clearEvents(): Completable

    fun saveEvents(events: List<EventEntity>): Completable

    fun getEvents(): Single<List<EventEntity>>

}