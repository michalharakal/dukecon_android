package org.dukecon.presentation.feature.event

import io.reactivex.observers.DisposableSingleObserver
import org.dukecon.domain.interactor.SingleUseCase
import javax.inject.Inject


class EventDatePresenter @Inject constructor(val getEventDateUseCase: SingleUseCase<List<String>, Void>)
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

    internal fun handleGetEventsSuccess(events: List<String>) {
        if (events == null || events.isEmpty()) {
            this.view?.showNoSessionDates()
        } else {
            this.view?.let {
                it.showSessionDates(events)
                it.scrollToCurrentDay() //.scrollTo(findCurrentSessionIndex(events))
            }
        }
    }

    inner class EventSubscriber : DisposableSingleObserver<List<String>>() {

        override fun onSuccess(t: List<String>) {
            handleGetEventsSuccess(t)
        }

        override fun onError(exception: Throwable) {
            view?.showNoSessionDates()
        }
    }
}