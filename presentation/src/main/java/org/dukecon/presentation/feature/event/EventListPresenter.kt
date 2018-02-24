package org.dukecon.presentation.feature.event

import io.reactivex.observers.DisposableSingleObserver
import org.dukecon.domain.features.event.GetEvents
import org.dukecon.domain.interactor.SingleUseCase
import org.dukecon.domain.model.Event
import org.dukecon.presentation.mapper.EventMapper
import org.joda.time.DateTime
import javax.inject.Inject

class EventListPresenter @Inject constructor(val getEventsUseCase: GetEvents,
                                             val eventsMapper: EventMapper) :
        EventListContract.Presenter {

    private var view: EventListContract.View? = null
    private var date: DateTime = DateTime.now()

    override fun onAttach(view: EventListContract.View) {
        this.view = view
    }

    override fun setDate(date: DateTime) {
        getEventsUseCase.execute(EventSubscriber(), date.dayOfMonth)


        /*
        speakerProvider.addSpeakerListener(this, { speakers ->
            if (speakers != null && speakers.isNotEmpty()) {
                this.view?.showSpeakers(speakers)
            }
        })

        favoriteProvider.removeFavoriteListener(date)
        favoriteProvider.addFavoriteListener(date, { sessions ->
            this.view?.showFavorites(sessions)
        })
        */
    }

    override fun onDetach() {
        this.view = null
        getEventsUseCase.dispose()
    }

    private fun findCurrentSessionIndex(sessions: List<Event>): Int {
        // find the current session index
        val index = sessions.indexOfFirst { it.startTime.isAfterNow() && it.endTime.isBeforeNow() }
        return index
    }

    internal fun handleGetEventsSuccess(events: List<Event>) {
        if (events.isEmpty()) {
            this.view?.showNoSessions()
        } else {
            this.view?.let {
                it.showSessions(events.map { eventsMapper.mapToView(it) })
                it.scrollTo(findCurrentSessionIndex(events))
            }
        }
        if (events.isNotEmpty()) {
            view?.showSessions(events.map { eventsMapper.mapToView(it) })
        } else {
            view?.showNoSessions()
        }
    }

    inner class EventSubscriber : DisposableSingleObserver<List<Event>>() {

        override fun onSuccess(t: List<Event>) {
            handleGetEventsSuccess(t)
        }

        override fun onError(exception: Throwable) {
            view?.showNoSessions()
        }
    }
}