package org.dukecon.presentation.feature.event

import io.reactivex.observers.DisposableSingleObserver
import org.dukecon.domain.interactor.SingleUseCase
import org.joda.time.DateTime
import javax.inject.Inject


class EventDatePresenter @Inject constructor(val getEventDateUseCase: SingleUseCase<List<DateTime>, Void>)
    : EventDateListContract.Presenter {

    private var view: EventDateListContract.View? = null

    override fun onAttach(view: EventDateListContract.View) {
        this.view = view

        getEventDateUseCase.execute(EventSubscriber())
    }

    override fun onDetach() {
        this.view = null
        getEventDateUseCase.dispose()
    }

    internal fun handleGetEventsSuccess(events: List<DateTime>) {
        if (events.isEmpty()) {
            this.view?.showNoSessionDates()
        } else {
            this.view?.let {
                it.showSessionDates(events)
                it.scrollToCurrentDay() //.scrollTo(findCurrentSessionIndex(events))
            }
        }
    }

    inner class EventSubscriber : DisposableSingleObserver<List<DateTime>>() {

        override fun onSuccess(t: List<DateTime>) {
            handleGetEventsSuccess(t)
        }

        override fun onError(exception: Throwable) {
            view?.showNoSessionDates()
        }
    }
}