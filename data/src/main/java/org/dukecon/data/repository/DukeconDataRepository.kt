package org.dukecon.data.repository

import org.dukecon.data.mapper.*
import org.dukecon.data.model.EventEntity
import org.dukecon.data.model.RoomEntity
import org.dukecon.data.model.SpeakerEntity
import org.dukecon.data.source.EventCacheDataStore
import org.dukecon.data.source.EventRemoteDataStore
import org.dukecon.domain.model.*
import org.dukecon.domain.repository.ConferenceRepository
import org.threeten.bp.OffsetDateTime
import javax.inject.Inject

/**
 * Provides an implementation of the [ConferenceRepository] interface for communicating to and from
 * data sources
 */
class DukeconDataRepository @Inject constructor(
        private val remoteDataStore: EventRemoteDataStore,
        private val localDataStore: EventCacheDataStore,
        private val eventMapper: EventMapper,
        private val speakerMapper: SpeakerMapper,
        private val keycloakMapper: KeycloakMapper,
        private val roomMapper: RoomMapper,
        private val feedbackMapper: FeedbackMapper,
        private val favoriteMapper: FavoriteMapper,
        private val metadataMapper: MetaDateMapper

) : ConferenceRepository {

    override var onRefreshListeners: List<() -> Unit> = emptyList()

    override suspend fun getKeyCloak(): Keycloak {
        return keycloakMapper.mapFromEntity(localDataStore.getKeycloak())
    }

    override suspend fun submitFeedback(feedback: Feedback): Any {
        return remoteDataStore.submitFeedback(feedbackMapper.mapToEntity(feedback))
    }

    override suspend fun saveFavorite(favorite: Favorite): List<Favorite> {
        localDataStore
                .saveFavorite(favoriteMapper.mapToEntity(favorite))
        return getFavorites()
    }

    override suspend fun getFavorites(): List<Favorite> {
        return localDataStore.getFavorites().map { list ->
            favoriteMapper.mapFromEntity(list)
        }
    }

    override suspend fun getSpeaker(id: String): Speaker {
        return speakerMapper.mapFromEntity(localDataStore.getSpeaker(id))
    }

    override suspend fun getEvent(id: String): Event {
        val dataStore = localDataStore

        val speakers = dataStore.getSpeakers().map { speakerMapper.mapFromEntity(it) }
        val favorites = dataStore.getFavorites().map { favoriteMapper.mapFromEntity(it) }
        val metaData = metadataMapper.mapFromEntity(dataStore.getMetaData())

        return eventMapper.mapFromEntity(dataStore.getEvent(id), speakers, favorites, metaData)
    }

    override suspend fun getRooms(): List<Room> {
        val dataStore = localDataStore
        return dataStore.getRooms().map { roomMapper.mapFromEntity(it) }
    }

    override suspend fun saveSpeakers(speakers: List<Speaker>) {
        val eventEntities = speakers.map { speakerMapper.mapToEntity(it) }
        return saveSpeakersEntities(eventEntities)
    }

    override suspend fun saveRooms(rooms: List<Room>) {
        val eventEntities = rooms.map { roomMapper.mapToEntity(it) }
        return savRoomsEntities(eventEntities)
    }

    override suspend fun getSpeakers(): List<Speaker> {
        val dataStore = localDataStore
        return dataStore.getSpeakers()
                .map {
                    speakerMapper.mapFromEntity(it)
                }.sortedBy { it.name }
    }

    override suspend fun getEventDates(): List<OffsetDateTime> {
        val dataStore = localDataStore
        return dataStore.getEvents()
                .distinctBy { it.startTime.dayOfMonth }
                .map {
                    it.startTime
                }.sortedBy { it.dayOfMonth }
    }

    override suspend fun saveEvents(events: List<Event>) {
        val eventEntities = events.map { eventMapper.mapToEntity(it) }
        return saveEventEntities(eventEntities)
    }

    private suspend fun saveEventEntities(events: List<EventEntity>) {
        return localDataStore.saveEvents(events)
    }

    private suspend fun saveSpeakersEntities(speakers: List<SpeakerEntity>) {
        return localDataStore.saveSpeakers(speakers)
    }

    private suspend fun savRoomsEntities(speakers: List<RoomEntity>) {
        return localDataStore.saveRooms(speakers)
    }

    private fun callRefreshListeners() {
        onRefreshListeners.forEach { it() }
    }


    override suspend fun update() {
        localDataStore.saveRooms(remoteDataStore.getRooms())
        localDataStore.saveEvents(remoteDataStore.getEvents())
        localDataStore.saveMetaData(remoteDataStore.getMetaData())
/*        remoteDataStore.getFavorites().map {
            localDataStore.saveFavorite(it)
        }
        */
        localDataStore.saveSpeakers(remoteDataStore.getSpeakers())

        callRefreshListeners()
    }

    override suspend fun getEvents(day: Int): List<Event> {
        val dataStore = localDataStore

        val speakers = dataStore.getSpeakers().map { speakerMapper.mapFromEntity(it) }
        val favorites = dataStore.getFavorites().map { favoriteMapper.mapFromEntity(it) }
        val events = dataStore.getEvents()
        val metaData = metadataMapper.mapFromEntity(dataStore.getMetaData())

        return events.allBy {
            if (day > 0) {
                it.startTime.dayOfMonth == day
            } else {
                true
            }
        }.map {
            eventMapper.mapFromEntity(it, speakers, favorites, metaData)
        }.sortedBy {
            it.startTime
        }
    }

    override suspend fun getMetaData(): MetaData {
        val dataStore = localDataStore

        return metadataMapper.mapFromEntity(dataStore.getMetaData())
    }


    /**
     * Returns a list containing elements from the given collection by the given [selector] function.
     */
    fun <T> Iterable<T>.allBy(selector: (T) -> Boolean): List<T> {
        val list = ArrayList<T>()
        for (e in this) {
            if (selector(e)) {
                list.add(e)
            }
        }
        return list
    }
}