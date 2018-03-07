package org.dukecon.android.cache.persistance

import com.google.gson.Gson
import org.dukecon.data.model.EventEntity
import org.dukecon.data.model.RoomEntity
import org.dukecon.data.model.SpeakerEntity
import java.io.*

class ConferenceCacheGsonSerializer(private val baseFolder: String, val gson: Gson) : ConferenceCacheSerializer {

    private fun getSpeakersFullName() = baseFolder + "/speakers.json"
    private fun getRoomsFullName() = baseFolder + "/rooms.json"
    private fun getEventsFullName() = baseFolder + "/events.json"

    override fun readSpeakers(): List<SpeakerEntity> {
        val sd = File(getSpeakersFullName())
        if (sd.exists()) {
            val inputStream = FileInputStream(sd)
            val reader = InputStreamReader(inputStream)
            return gson.fromJson(reader, Array<SpeakerEntity>::class.java).toList()
        } else {
            return listOf()
        }
    }

    override fun readEvents(): List<EventEntity> {
        val sd = File(getEventsFullName())
        if (sd.exists()) {
            val inputStream = FileInputStream(sd)
            val reader = InputStreamReader(inputStream)

            return gson.fromJson(reader, Array<EventEntity>::class.java).toList()
        } else {
            return listOf()
        }
    }

    override fun readRooms(): List<RoomEntity> {
        val sd = File(getRoomsFullName())
        if (sd.exists()) {
            val inputStream = FileInputStream(sd)
            val reader = InputStreamReader(inputStream)

            return gson.fromJson(reader, Array<RoomEntity>::class.java).toList()
        } else {
            return listOf()
        }
    }

    override fun writeRooms(cachedRooms: List<RoomEntity>) {
        writeList(getRoomsFullName(), cachedRooms)
    }

    override fun writeEvents(events: List<EventEntity>) {
        writeList(getEventsFullName(), events)
    }

    override fun writeSpeakers(speakers: List<SpeakerEntity>) {
        writeList(getSpeakersFullName(), speakers)
    }

    fun writeList(fileName: String, events: List<Any>) {
        val sd = File(fileName)
        sd.createNewFile()

        val fOut = FileOutputStream(sd)
        val myOutWriter = OutputStreamWriter(fOut)
        val json = gson.toJson(events)
        myOutWriter.write(json)
        myOutWriter.flush()
    }
}

interface ConferenceCacheSerializer {
    fun readSpeakers(): List<SpeakerEntity>
    fun readEvents(): List<EventEntity>
    fun readRooms(): List<RoomEntity>
    fun writeRooms(cachedRooms: List<RoomEntity>)
    fun writeEvents(events: List<EventEntity>)
    fun writeSpeakers(speakers: List<SpeakerEntity>)
}
