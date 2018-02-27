package org.dukecon.remote.mapper

import org.dukecon.data.model.EventEntity
import org.dukecon.data.model.SpeakerEntity
import javax.inject.Inject


/**
 * Map a [Speaker] to and from a [EventEntity] instance when data is moving between
 * this later and the Data layer
 */
open class SpeakerEntityMapper @Inject constructor() : EntityMapper<org.dukecon.android.api.model.Speaker, SpeakerEntity> {

    /**
     * Map an instance of a [org.dukecon.android.api.model.Speaker] to a [SpeakerEntity] model
     */
    override fun mapFromRemote(type: org.dukecon.android.api.model.Speaker): SpeakerEntity {
        return SpeakerEntity(type.id, type.name, type.company ?: "", getAvatarUrlFromId(type.photoId ?: ""))
    }

    private fun getAvatarUrlFromId(s: String): String {
        return "https://programm.javaland.eu/2018/rest/speaker/images/" + s
    }

}