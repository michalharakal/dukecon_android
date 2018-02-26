package org.dukecon.presentation.feature.eventdetail

import io.reactivex.observers.DisposableSingleObserver
import org.dukecon.domain.features.eventdetail.GetEventDetailUseCase
import org.dukecon.domain.model.Event
import org.dukecon.presentation.mapper.EventMapper
import javax.inject.Inject


class EventDetailPresenter @Inject constructor(val eventDetailUseCase: GetEventDetailUseCase,
                                               val eventsMapper: EventMapper) :
        EventDetailContract.Presenter {

    private var view: EventDetailContract.View? = null

    override fun onAttach(view: EventDetailContract.View) {
        this.view = view
    }

    override fun onDetach() {
        this.view = null
        eventDetailUseCase.dispose()
    }

    override fun toggleFavorite() {

    }

    override fun setSessionId(sessionId: String) {
        eventDetailUseCase.execute(EventDetailsSubscriber(), sessionId)
    }

    private fun handleGetEventSuccess(event: Event) {
        this.view?.let {
            it.showSessionDetail(eventsMapper.mapToView(event))
        }
    }

    inner class EventDetailsSubscriber : DisposableSingleObserver<Event>() {

        override fun onSuccess(t: Event) {
            handleGetEventSuccess(t)
        }

        override fun onError(exception: Throwable) {
            view?.showNoSession()
        }
    }


}