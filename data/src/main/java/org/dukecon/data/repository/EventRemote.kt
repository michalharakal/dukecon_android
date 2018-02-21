package org.dukecon.data.repository

import io.reactivex.Single
import org.dukecon.data.model.EventEntity

interface EventRemote {

    fun getEvents(): Single<List<EventEntity>>

}