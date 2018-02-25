package org.dukecon.data.repository

import io.reactivex.Single
import org.dukecon.data.model.EventEntity
import org.dukecon.data.model.SpeakerEntity

interface EventRemote {

    fun getEvents(): Single<List<EventEntity>>
    fun getSpeakers(): Single<List<SpeakerEntity>>

}