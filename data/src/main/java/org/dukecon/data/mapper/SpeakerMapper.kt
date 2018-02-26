package org.dukecon.data.mapper

import org.dukecon.data.model.SpeakerEntity
import org.dukecon.domain.model.Speaker
import javax.inject.Inject


/**
 * Map a [SpeakerEntity] to and from a [Speaker] instance when data is moving between
 * this later and the Domain layer
 */
open class SpeakerMapper @Inject constructor() : Mapper<SpeakerEntity, Speaker> {

    /**
     * Map a [SpeakerEntity] instance to a [Speaker] instance
     */
    override fun mapFromEntity(type: SpeakerEntity): Speaker {
        return Speaker(type.id, type.name)
    }

    /**
     * Map a [Speaker] instance to a [SpeakerEntity] instance
     */
    override fun mapToEntity(type: Speaker): SpeakerEntity {
        return SpeakerEntity(type.id, type.name)
    }
}