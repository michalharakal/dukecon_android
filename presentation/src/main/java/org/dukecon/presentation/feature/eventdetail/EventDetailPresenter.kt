package org.dukecon.presentation.feature.eventdetail

import io.reactivex.observers.DisposableSingleObserver
import org.dukecon.domain.features.eventdetail.GetEventDetailUseCase
import org.dukecon.domain.model.Event
import org.dukecon.domain.model.Speaker
import org.dukecon.presentation.mapper.EventMapper
import org.dukecon.presentation.mapper.SpeakerMapper
import org.dukecon.presentation.model.SpeakerView
import javax.inject.Inject


class EventDetailPresenter @Inject constructor(val eventDetailUseCase: GetEventDetailUseCase,
                                               val speakerMapper: SpeakerMapper,
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
            it.showSpeakerInfo(event.speakerIds.map { it -> speakerMapper.mapToView(it) })
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