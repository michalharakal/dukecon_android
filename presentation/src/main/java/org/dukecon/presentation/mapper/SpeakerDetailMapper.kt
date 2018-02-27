package org.dukecon.presentation.mapper

import org.dukecon.domain.model.Speaker
import org.dukecon.presentation.model.SpeakerDetailView
import javax.inject.Inject

/**
 * Map a [SpeakerDetailView] to and from a [Speaker] instance when data is moving between
 * this layer and the Domain layer
 */
open class SpeakerDetailMapper @Inject constructor() : Mapper<SpeakerDetailView, Speaker> {

    /**
     * Map a [Speaker] instance to a [SpeakerDetailView] instance
     */
    override fun mapToView(type: Speaker): SpeakerDetailView {
        return SpeakerDetailView(type.id, type.name, type.title, type.twitter, type.bio, type.website, type.avatar)
    }
}