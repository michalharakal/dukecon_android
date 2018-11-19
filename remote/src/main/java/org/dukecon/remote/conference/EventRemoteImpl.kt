package org.dukecon.remote.conference

import io.reactivex.Single
import org.dukecon.android.api.ConferencesApi
import org.dukecon.android.api.model.Event
import org.dukecon.android.api.model.Speaker
import org.dukecon.data.model.EventEntity
import org.dukecon.data.model.FeedbackEntity
import org.dukecon.data.model.KeycloakEntity
import org.dukecon.data.model.RoomEntity
import org.dukecon.data.model.SpeakerEntity
import org.dukecon.data.repository.EventRemote
import org.dukecon.remote.conference.mapper.EventEntityMapper
import org.dukecon.remote.conference.mapper.FeedbackEntityMapper
import org.dukecon.remote.conference.mapper.KeycloakEntityMapper
import org.dukecon.remote.conference.mapper.RoomEntityMapper
import org.dukecon.remote.conference.mapper.SpeakerEntityMapper
import java.io.IOException
import javax.inject.Inject

/**
 * Remote implementation for retrieving Event instances. This class implements the
 * [EventRemote] from the Data layer as it is that layers responsibility for defining the
 * operations in which data store implementation layers can carry out.
 */
class EventRemoteImpl @Inject constructor(
    private val conferenceApi: ConferencesApi,
    private val conferenceId: String,
    private val entityMapper: EventEntityMapper,
    private val feedbackEntityMapper: FeedbackEntityMapper,
    private val speakersEntityMapper: SpeakerEntityMapper,
    private val roomEntityMapper: RoomEntityMapper,
    private val keycloakEntityMapper: KeycloakEntityMapper
) : EventRemote {
    override fun getKeycloak(): Single<KeycloakEntity> {
        return Single.create { s ->
            val call = conferenceApi.getKeyCloak(conferenceId)
            try {
                val response = call.execute()
                if (response.isSuccessful) {
                    val keycloak = response.body()
                    if (keycloak != null) {
                        s.onSuccess(keycloakEntityMapper.mapFromRemote(keycloak))
                    }
                } else {
                    s.onError(Throwable())
                }
            } catch (e: IOException) {
                s.onError(e)
            }
        }
    }

    override fun submitFeedback(feedback: FeedbackEntity): Single<Any> {
        return Single.create { s ->
            val call = conferenceApi.updateFeedback(
                conferenceId,
                feedback.sessionId,
                feedbackEntityMapper.mapToRemote(feedback)
            )
            try {
                val response = call.execute()
                if (response.isSuccessful) {
                    s.onSuccess(Any())
                } else {
                    s.onError(Throwable())
                }
            } catch (e: IOException) {
                s.onError(e)
            }
        }
    }

    override fun getSpeaker(id: String): Single<SpeakerEntity> {
        return Single.create { s ->
            val call = conferenceApi.getSpeakers(conferenceId)
            try {
                val response = call.execute()
                if (response.isSuccessful) {
                    if (response.body() != null) {
                        val eventsList = response.body()
                        if (eventsList != null) {
                            val found = eventsList.find { event ->
                                event.id.equals(id)
                            } ?: emptySpeakerEntity()
                            s.onSuccess(speakersEntityMapper.mapFromRemote(found))
                        }
                    }
                } else {
                    s.onError(Throwable())
                }
            } catch (e: IOException) {
                s.onError(e)
            }
        }
    }

    private fun emptySpeakerEntity(): Speaker {
        val speaker = Speaker()
        speaker.id = ""
        return speaker
    }

    override fun getEvent(id: String): Single<EventEntity> {
        return Single.create { s ->
            val call = conferenceApi.getEvents(conferenceId)
            try {
                val response = call.execute()
                if (response.isSuccessful) {
                    if (response.body() != null) {
                        val eventsList = response.body()
                        if (eventsList != null) {
                            val found = eventsList.find { event ->
                                event.id.equals(id)
                            } ?: emptyEntity()
                            s.onSuccess(entityMapper.mapFromRemote(found))
                        }
                    }
                } else {
                    s.onError(Throwable())
                }
            } catch (ex: IOException) {
                s.onError(ex)
            }
        }
    }

    private fun emptyEntity(): Event {
        val event = Event()
        event.id = ""
        return event
    }

    override fun getRooms(): Single<List<RoomEntity>> {
        return Single.create { s ->
            val call = conferenceApi.getMeta(conferenceId)
            try {
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
            } catch (e: IOException) {
                s.onError(e)
            }
        }
    }

    override fun getSpeakers(): Single<List<SpeakerEntity>> {
        return Single.create { s ->
            val call = conferenceApi.getSpeakers(conferenceId)
            try {
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
            } catch (e: IOException) {
                s.onError(e)
            }
        }
    }

    /**
     * Retrieve a list of [EventEntity] instances from the [ConferencesApi].
     */
    override fun getEvents(): Single<List<EventEntity>> {
        return Single.create { s ->
            val call = conferenceApi.getEvents(conferenceId);
            try {
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
            } catch (e: IOException) {
                s.onError(e)
            }
        }
    }
}