package org.dukecon.data.repository

import com.jakewharton.rxrelay2.PublishRelay
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.functions.BiFunction
import org.dukecon.data.mapper.EventMapper
import org.dukecon.data.mapper.FavoriteMapper
import org.dukecon.data.mapper.RoomMapper
import org.dukecon.data.mapper.SpeakerMapper
import org.dukecon.data.model.EventEntity
import org.dukecon.data.model.FavoriteEntity
import org.dukecon.data.model.RoomEntity
import org.dukecon.data.model.SpeakerEntity
import org.dukecon.data.source.EventDataStoreFactory
import org.dukecon.data.source.EventRemoteDataStore
import org.dukecon.domain.model.*
import org.dukecon.domain.repository.ConferenceRepository
import org.joda.time.DateTime
import javax.inject.Inject


/**
 * Provides an implementation of the [ConferenceRepository] interface for communicating to and from
 * data sources
 */
class DukeconDataRepository @Inject constructor(private val factory: EventDataStoreFactory,
                                                private val eventMapper: EventMapper,
                                                private val speakerMapper: SpeakerMapper,
                                                private val roomMapper: RoomMapper,
                                                private val favoriteMapper: FavoriteMapper) :
        ConferenceRepository {

    private var relay: PublishRelay<Change> = PublishRelay.create<Change>()

    override fun getEventChanges(): Observable<Change> {
        return relay
    }

    override fun saveFavorite(favorite: Favorite): Single<List<Favorite>> {
        return factory.retrieveCacheDataStore()
                .saveFavorite(favoriteMapper.mapToEntity(favorite))
                .doAfterSuccess({
                    relay.accept(Change(DataChange.FAVORITE))
                })
                .map { list ->
                    list.map { listItem ->
                        favoriteMapper.mapFromEntity(listItem)
                    }
                }

    }

    override fun getFavorites(): Single<List<Favorite>> {
        return factory.retrieveCacheDataStore().getFavorites().map { list ->
            list.map { listItem ->
                favoriteMapper.mapFromEntity(listItem)
            }
        }
    }

    override fun getSpeaker(id: String): Single<Speaker> {
        val dataStore = factory.retrieveDataStore()

        return dataStore.getSpeaker(id).map { speakerMapper.mapFromEntity(it) }
    }

    override fun getEvent(id: String): Single<Event> {
        val dataStore = factory.retrieveDataStore()

        val getEvent = dataStore.getEvent(id).map { eventMapper.mapFromEntity(it) }
        val getRooms = dataStore.getRooms()
        val getSpeakers = dataStore.getSpeakers()
        val getFavorites = factory.retrieveCacheDataStore().getFavorites()

        val eventsWithRooms = Single.zip(getEvent, getRooms, BiFunction { event: Event, rooms: List<RoomEntity> ->
            combineEvent(event, rooms)
        })
        val eventWithRoomsAndSpeaker = Single.zip(eventsWithRooms, getSpeakers, BiFunction { event: Event, rooms: List<SpeakerEntity> ->
            combineSpeaker(event, rooms)
        })
        return Single.zip(eventWithRoomsAndSpeaker, getFavorites, BiFunction { event: Event, favorites: List<FavoriteEntity> ->
            combineFavorites(event, favorites)
        })

    }

    private fun combineFavorites(event: Event, favorites: List<FavoriteEntity>): Event {
        val foundEvent = favorites.find { it.id.equals(event.name) }

        if (foundEvent != null) {
            return Event(event.name, event.title, event.description, event.startTime, event.endTime,
                    event.speakers, Favorite(event.name, true), event.name)
        } else {
            return event
        }
    }

    private fun combineEvent(event: Event, rooms: List<RoomEntity>): Event {
        val foundRoom = rooms.find { it.id.equals(event.room) }

        if (foundRoom != null) {
            return Event(event.name, event.title, event.description, event.startTime, event.endTime,
                    event.speakers, event.favorite, foundRoom.name)
        } else {
            return event
        }
    }

    private fun combineSpeaker(event: Event, speakers: List<SpeakerEntity>): Event {
        val foundSpeakers: MutableList<Speaker> = mutableListOf()
        event.speakers.forEach { eventSpeaker ->
            run {
                val found = speakers.find { it.id.equals(eventSpeaker.id) }
                if (found != null) {
                    foundSpeakers.add(speakerMapper.mapFromEntity(found))
                }
            }
        }

        if (foundSpeakers.isNotEmpty()) {
            return Event(event.name, event.title, event.description, event.startTime, event.endTime,
                    foundSpeakers, event.favorite, event.room)
        } else {
            return event
        }
    }

    override fun getRooms(): Single<List<Room>> {
        val dataStore = factory.retrieveDataStore()
        return dataStore.getRooms()
                .flatMap {
                    if (dataStore is EventRemoteDataStore) {
                        savRoomsEntities(it).toSingle { it }
                    } else {
                        Single.just(it)
                    }
                }.map { list ->
                    list.map { listItem ->
                        roomMapper.mapFromEntity(listItem)
                    }
                }
    }

    override fun saveSpeakers(speakers: List<Speaker>): Completable {
        val eventEntities = speakers.map { speakerMapper.mapToEntity(it) }
        return saveSpeakersEntities(eventEntities)

    }

    override fun saveRooms(rooms: List<Room>): Completable {
        val eventEntities = rooms.map { roomMapper.mapToEntity(it) }
        return savRoomsEntities(eventEntities)
    }

    override fun getSpeakers(): Single<List<Speaker>> {
        val dataStore = factory.retrieveDataStore()
        return dataStore.getSpeakers()
                .flatMap {
                    if (dataStore is EventRemoteDataStore) {
                        saveSpeakersEntities(it).toSingle { it }
                    } else {
                        Single.just(it)
                    }
                }.map { list ->
                    list.map { listItem ->
                        speakerMapper.mapFromEntity(listItem)
                    }.sortedBy { it -> it.name }
                }
    }

    override fun getEventDates(): Single<List<DateTime>> {
        val dataStore = factory.retrieveDataStore()
        return dataStore.getEvents()
                .flatMap {
                    if (dataStore is EventRemoteDataStore) {
                        saveEventEntities(it).toSingle() { it }

                    } else {
                        Single.just(it.distinctBy {
                            it.startTime.dayOfMonth()
                        })
                    }
                }
                .map { list ->
                    list.distinctBy {
                        it.startTime.dayOfMonth()
                    }.sortedBy { it.startTime }

                }
                .map { list ->
                    list.map {
                        it.startTime
                    }
                }
    }

    override fun clearEvents(): Completable {
        return factory.retrieveCacheDataStore().clearEvents()
    }

    override fun saveEvents(events: List<Event>): Completable {
        val eventEntities = events.map { eventMapper.mapToEntity(it) }
        return saveEventEntities(eventEntities)
    }

    private fun saveEventEntities(events: List<EventEntity>): Completable {
        return factory.retrieveCacheDataStore().saveEvents(events)
    }

    private fun saveSpeakersEntities(speakers: List<SpeakerEntity>): Completable {
        return factory.retrieveCacheDataStore().saveSpeakers(speakers)
    }

    private fun savRoomsEntities(speakers: List<RoomEntity>): Completable {
        return factory.retrieveCacheDataStore().saveRooms(speakers)
    }


    override fun getEvents(day: Int): Single<List<Event>> {
        val dataStore = factory.retrieveDataStore()

        val getFavorites = factory.retrieveCacheDataStore().getFavorites()


        val getEvents = dataStore.getEvents()
                .flatMap {
                    if (dataStore is EventRemoteDataStore) {
                        saveEventEntities(it).toSingle { it }
                    } else {
                        Single.just(it)
                    }
                }
                .map { list ->
                    list.map { listItem ->
                        eventMapper.mapFromEntity(listItem)
                    }
                }
                .map {
                    it.sortedBy { it.startTime }
                }
                .map {
                    it.allBy {
                        if (day > 0) {
                            it.startTime.dayOfMonth().get() == day
                        } else {
                            true
                        }
                    }
                }
        return Single.zip(getEvents, getFavorites, BiFunction { event: List<Event>, favorites: List<FavoriteEntity> ->
            combineFavoritesList(event, favorites)
        })

    }

    private fun combineFavoritesList(events: List<Event>, favorites: List<FavoriteEntity>): List<Event> {
        if (favorites.size > 0) {

            val newlist: MutableList<Event> = mutableListOf()

            for (event in events) {
                val foundEvent = favorites.find { it.id.equals(event.name) }

                if (foundEvent != null) {
                    newlist.add(Event(event.name, event.title, event.description, event.startTime, event.endTime,
                            event.speakers, Favorite(event.name, true), event.room))
                } else {
                    newlist.add(Event(event.name, event.title, event.description, event.startTime, event.endTime,
                            event.speakers, Favorite(event.name, false), event.room))
                }
            }
            return newlist
        } else {
            return events
        }
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