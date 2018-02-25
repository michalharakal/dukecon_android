package org.dukecon.remote.mapper

import com.sun.xml.internal.bind.v2.runtime.unmarshaller.XsiNilLoader
import io.reactivex.Single
import io.reactivex.Single.fromObservable
import io.reactivex.SingleEmitter
import org.dukecon.android.api.ConferencesApi
import org.dukecon.android.api.model.Event
import org.dukecon.data.model.EventEntity
import org.dukecon.data.model.RoomEntity
import org.dukecon.data.model.SpeakerEntity
import org.dukecon.data.repository.EventRemote
import javax.inject.Inject


/**
 * Remote implementation for retrieving Event instances. This class implements the
 * [EventRemote] from the Data layer as it is that layers responsibility for defining the
 * operations in which data store implementation layers can carry out.
 */
class EventRemoteImpl @Inject constructor(private val conferenceApi: ConferencesApi,
                                          private val conferenceId: String,
                                          private val entityMapper: EventEntityMapper,
                                          private val speakersEntityMapper: SpeakerEntityMapper,
                                          private val roomEntityMapper: RoomEntityMapper) :
        EventRemote {
    override fun getRooms(): Single<List<RoomEntity>> {
        return Single.create({ s ->
            val call = conferenceApi.getMeta(conferenceId);
            val response = call.execute()
            if (response.isSuccessful) {
                if (response.body() != null) {
                    val eventsList = response.body()
                    if (eventsList != null) {
                        s.onSuccess(
                                eventsList.locations.map { roomEntityMapper.mapFromRemote(it) }
                        )
                    }
                }
            } else {
                s.onError(Throwable())
            }
        })
    }

    override fun getSpeakers(): Single<List<SpeakerEntity>> {
        return Single.create({ s ->
            val call = conferenceApi.getSpeakers(conferenceId);
            val response = call.execute()
            if (response.isSuccessful) {
                if (response.body() != null) {
                    val eventsList = response.body()
                    if (eventsList != null) {
                        s.onSuccess(
                                eventsList.map { speakersEntityMapper.mapFromRemote(it) }
                        )
                    }

                }
            } else {
                s.onError(Throwable())
            }
        })
    }

    /**
     * Retrieve a list of [EventEntity] instances from the [ConferencesApi].
     */
    override fun getEvents(): Single<List<EventEntity>> {
        return Single.create({ s ->
            val call = conferenceApi.getEvents(conferenceId);
            val response = call.execute()
            if (response.isSuccessful) {
                if (response.body() != null) {
                    val eventsList = response.body()
                    if (eventsList != null) {
                        s.onSuccess(
                                eventsList.map { entityMapper.mapFromRemote(it) }
                        )
                    }

                }
            } else {
                s.onError(Throwable())
            }
        })
    }
}