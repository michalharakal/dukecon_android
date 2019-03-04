package org.dukecon.presentation.feature.event

import kotlinx.coroutines.launch
import org.dukecon.domain.repository.ConferenceRepository
import org.dukecon.presentation.CoroutinePresenter
import javax.inject.Inject


class EventDatePresenter @Inject constructor(val conferenceRepository: ConferenceRepository)
    : CoroutinePresenter<EventDateListContract.View>(), EventDateListContract.Presenter {

    override fun showError(error: Throwable) {
    }

    private lateinit var view: EventDateListContract.View

    override fun onAttach(view: EventDateListContract.View) {
        this.view = view
        launch {
            val dates = conferenceRepository.getEventDates()

            if (dates.isEmpty()) {
                view.showNoSessionDates()
            } else {
                view.let {
                    it.showSessionDates(dates)
                    it.scrollToCurrentDay()
                }
            }
        }
    }

    override fun onDetach() {
    }
}