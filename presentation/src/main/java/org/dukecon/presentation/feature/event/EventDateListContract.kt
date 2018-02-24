package org.dukecon.presentation.feature.event

import org.dukecon.presentation.BasePresenter
import org.dukecon.presentation.BaseView
import org.joda.time.DateTime

interface EventDateListContract {

    interface View : BaseView {
        fun showNoSessionDates()
        fun showSessionDates(sessionDates: List<DateTime>)
        fun scrollToCurrentDay()
    }

    interface Presenter : BasePresenter<View> {
    }

}