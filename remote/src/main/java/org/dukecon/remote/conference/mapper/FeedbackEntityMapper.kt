package org.dukecon.remote.conference.mapper

import org.dukecon.android.api.model.Feedback
import org.dukecon.data.model.EventEntity
import org.dukecon.data.model.FeedbackEntity
import javax.inject.Inject

/**
 * Map a [Event] to and from a [EventEntity] instance when data is moving between
 * this later and the Data layer
 */
open class FeedbackEntityMapper @Inject constructor() {

    /**
     * Map an instance of a [FeedbackEntity] to a [org.dukecon.android.api.model.Feedback] model
     */
    fun mapToRemote(type: FeedbackEntity): Feedback {
        val result = Feedback()
        result.comment = type.comment
        result.rating = type.value
        return result
    }
}