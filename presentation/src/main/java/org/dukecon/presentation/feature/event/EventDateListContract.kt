package org.dukecon.presentation.feature.event

import org.dukecon.presentation.BasePresenter
import org.dukecon.presentation.BaseView

interface EventDateListContract {

    interface View : BaseView {
        fun showNoSessionDates()
        fun showSessionDates(sessionDates: List<String>)
        fun scrollToCurrentDay()
    }

    interface Presenter : BasePresenter<View> {
    }

}