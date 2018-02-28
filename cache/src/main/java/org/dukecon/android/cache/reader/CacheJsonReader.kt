package org.dukecon.android.cache.reader

import com.google.gson.Gson
import org.dukecon.data.model.EventEntity
import org.dukecon.data.model.RoomEntity
import org.dukecon.data.model.SpeakerEntity
import java.io.File
import java.io.FileInputStream
import java.io.InputStreamReader
import java.io.Reader

class CacheJsonReader(private val fullFileName: String, val gson: Gson) {


    fun readSpeakersFromJson(): List<SpeakerEntity> {
        val sd = File(fullFileName)
        if (sd.exists()) {
            val inputStream = FileInputStream(sd)
            val reader = InputStreamReader(inputStream)
            return gson.fromJson(reader, Array<SpeakerEntity>::class.java).toList()
        } else {
            return listOf()
        }
    }

    fun readEventsFromJson(): List<EventEntity> {
        val sd = File(fullFileName)
        if (sd.exists()) {
            val inputStream = FileInputStream(sd)
            val reader = InputStreamReader(inputStream)

            return gson.fromJson(reader, Array<EventEntity>::class.java).toList()
        } else {
            return listOf()
        }
    }

    fun readRoomsFromJson(): List<RoomEntity> {
        val sd = File(fullFileName)
        if (sd.exists()) {
            val inputStream = FileInputStream(sd)
            val reader = InputStreamReader(inputStream)

            return gson.fromJson(reader, Array<RoomEntity>::class.java).toList()
        } else {
            return listOf()
        }
    }

}

inline fun <reified T> Gson.fromJson(reader: Reader): T =
        this.fromJson<T>(reader, T::class.java)

