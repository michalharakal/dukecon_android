package org.dukecon.presentation.feature.event

import org.dukecon.presentation.BasePresenter
import org.dukecon.presentation.BaseView
import org.dukecon.presentation.model.EventView
import org.dukecon.presentation.model.RoomView
import org.dukecon.presentation.model.SpeakerView
import org.joda.time.DateTime

/**
 * Defines a contract of operations between the Events Presenter and Events View
 */
interface EventListContract {

    interface View : BaseView {

        fun showNoSessions()
        fun showSessions(sessions: List<EventView>)
        fun showSpeakers(speakers: Map<String, SpeakerView>)
        fun showRooms(speakers: Map<String, RoomView>)
        fun showFavorites(favorites: Set<String>)
        fun scrollTo(index: Int)

    }

    interface Presenter : BasePresenter<View> {
        fun setDate(conferenceDay: DateTime)
    }

}