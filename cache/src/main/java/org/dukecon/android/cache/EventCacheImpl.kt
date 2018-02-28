package org.dukecon.android.cache

import com.google.gson.Gson
import io.reactivex.Completable
import io.reactivex.Single
import org.dukecon.android.cache.reader.CacheJsonReader
import org.dukecon.data.model.EventEntity
import org.dukecon.data.model.RoomEntity
import org.dukecon.data.model.SpeakerEntity
import org.dukecon.data.repository.EventCache
import org.joda.time.DateTime
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStreamWriter
import javax.inject.Inject

/**
 * Cached implementation for retrieving and saving Event instances. This class implements the
 * [EventCache] from the Data layer as it is that layers responsibility for defining the
 * operations in which data store implementation layers can carry out. Just simple in memory chache
 */
class EventCacheImpl @Inject constructor(val baseCacheFolder: String, val gson: Gson, val preferencesHelper: PreferencesHelper) :
        EventCache {

    var cachedRooms: List<RoomEntity> = listOf()
    var cachedEvents: List<EventEntity> = listOf()
    var cacheSpeakers: List<SpeakerEntity> = listOf()

    // 1 hour cache
    private val EXPIRATION_TIME = (60 * 60 * 1000).toLong()

    init {
        if (preferencesHelper.lastCacheTime > 0) {
            readRooms()
            readEvents()
            readSpeakers()
        }
    }

    private fun readRooms() {
        val reader = CacheJsonReader(baseCacheFolder + "/rooms.json", gson)
        cachedRooms = reader.readRoomsFromJson()
    }

    override fun saveRooms(rooms: List<RoomEntity>): Completable {
        cachedRooms = rooms
        writeRooms()
        preferencesHelper.lastCacheTime = System.currentTimeMillis()
        return Completable.complete()
    }

    override fun getRooms(): Single<List<RoomEntity>> {
        return Single.create({ s ->
            s.onSuccess(
                    cachedRooms
            )
        })
    }


    override fun clearEvents(): Completable {
        preferencesHelper.lastCacheTime = 0
        cachedEvents = listOf()
        cacheSpeakers = listOf()
        cachedRooms = listOf()
        return Completable.complete()
    }

    override fun saveEvents(events: List<EventEntity>): Completable {
        cachedEvents = events
        writeEvents()
        preferencesHelper.lastCacheTime = System.currentTimeMillis()
        return Completable.complete()
    }

    private fun writeEvents() {
        val sd = File(baseCacheFolder + "/events.json")
        sd.createNewFile()

        val fOut = FileOutputStream(sd)
        val myOutWriter = OutputStreamWriter(fOut)
        val json = gson.toJson(cachedEvents)
        myOutWriter.write(json)
        myOutWriter.flush()
    }

    private fun readEvents() {
        val reader = CacheJsonReader(baseCacheFolder + "/events.json", gson)
        cachedEvents = reader.readEventsFromJson()
    }


    private fun writeSpeakers() {
        val sd = File(baseCacheFolder + "/speakers.json")
        sd.createNewFile()

        val fOut = FileOutputStream(sd)
        val myOutWriter = OutputStreamWriter(fOut)
        val json = gson.toJson(cacheSpeakers)
        myOutWriter.write(json)
        myOutWriter.flush()
    }

    private fun writeRooms() {
        val sd = File(baseCacheFolder + "/rooms.json")
        sd.createNewFile()

        val fOut = FileOutputStream(sd)
        val myOutWriter = OutputStreamWriter(fOut)
        val json = gson.toJson(cachedRooms)
        myOutWriter.write(json)
        myOutWriter.flush()
    }

    override fun getSpeakers(): Single<List<SpeakerEntity>> {
        return Single.create({ s ->
            s.onSuccess(
                    cacheSpeakers
            )
        })
    }

    override fun saveSpeakers(speakers: List<SpeakerEntity>): Completable {
        cacheSpeakers = speakers
        writeSpeakers()
        preferencesHelper.lastCacheTime = System.currentTimeMillis()
        return Completable.complete()
    }

    private fun readSpeakers() {
        val reader = CacheJsonReader(baseCacheFolder + "/speakers.json", gson)
        cacheSpeakers = reader.readSpeakersFromJson()
    }


    override fun getEvents(): Single<List<EventEntity>> {
        return Single.create({ s ->
            s.onSuccess(
                    cachedEvents
            )
        })
    }

    override fun getEvent(id: String): Single<EventEntity> {
        return Single.create({ s ->
            val found = cachedEvents.find { event ->
                event.id.equals(id)
            } ?: emptyEntity()
            s.onSuccess(found)
        })
    }

    override fun getSpeaker(id: String): Single<SpeakerEntity> {
        return Single.create({ s ->
            val found = cacheSpeakers.find { speaker ->
                speaker.id.equals(id)
            } ?: emptySpeakerEntity()
            s.onSuccess(found)
        })

    }

    private fun emptySpeakerEntity(): SpeakerEntity {
        return SpeakerEntity("", "", "", "", "", "", "")
    }


    private fun emptyEntity(): EventEntity {
        val event = EventEntity("", "", "", DateTime(), DateTime(), listOf(), "")
        return event
    }


    override fun isCached(): Boolean {
        return cachedEvents.isNotEmpty() && cacheSpeakers.isNotEmpty() && cachedRooms.isNotEmpty()
    }

    override fun setLastCacheTime(lastCache: Long) {
        preferencesHelper.lastCacheTime = System.currentTimeMillis()
    }

    override fun isExpired(): Boolean {
        val currentTime = System.currentTimeMillis()
        val lastUpdateTime = preferencesHelper.lastCacheTime

        return currentTime - lastUpdateTime > EXPIRATION_TIME
    }
}