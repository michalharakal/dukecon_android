package org.dukecon.remote.conference.mapper

import org.dukecon.android.api.model.Feedback
import org.dukecon.data.model.FeedbackEntity
import javax.inject.Inject

class FeedbackEntityMapper @Inject constructor() {

    fun mapToRemote(type: FeedbackEntity): Feedback {
        val result = Feedback()
        result.comment = type.comment
        result.rating = type.value
        return result
    }
}