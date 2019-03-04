package org.dukecon.presentation.feature.event

import org.dukecon.presentation.BasePresenter
import org.dukecon.presentation.BaseView
import org.dukecon.presentation.model.EventView
import org.threeten.bp.OffsetDateTime

/**
 * Defines a contract of operations between the Events Presenter and Events View
 */
interface EventListContract {

    interface View : BaseView {
        fun showNoSessions()
        fun showSessions(sessions: List<EventView>)
        fun scrollTo(index: Int)
    }

    interface Presenter : BasePresenter<View> {
        fun setDate(conferenceDay: OffsetDateTime, showFavoritesOnly: Boolean)
    }

}