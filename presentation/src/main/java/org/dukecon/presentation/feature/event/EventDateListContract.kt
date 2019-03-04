package org.dukecon.presentation.feature.event

import org.dukecon.presentation.BasePresenter
import org.dukecon.presentation.BaseView
import org.threeten.bp.OffsetDateTime

interface EventDateListContract {

    interface View : BaseView {
        fun showNoSessionDates()
        fun showSessionDates(sessionDates: List<OffsetDateTime>)
        fun scrollToCurrentDay()
    }

    interface Presenter : BasePresenter<View>

}