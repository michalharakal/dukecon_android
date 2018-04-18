package org.dukecon.presentation.feature.event

import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import org.dukecon.domain.features.event.GetEvents
import org.dukecon.domain.features.event.GetRooms
import org.dukecon.domain.features.event.GetSpeakers
import org.dukecon.domain.features.time.CurrentTimeProvider
import org.dukecon.domain.model.Event
import org.dukecon.domain.model.Room
import org.dukecon.domain.model.Speaker
import org.dukecon.domain.repository.ConferenceRepository
import org.dukecon.presentation.mapper.EventMapper
import org.dukecon.presentation.mapper.RoomMapper
import org.dukecon.presentation.mapper.SpeakerMapper
import org.joda.time.DateTime
import org.joda.time.Duration
import javax.inject.Inject

class EventListPresenter @Inject constructor(val currentTimeProvider: CurrentTimeProvider,
                                             val conferenceRepository: ConferenceRepository,
                                             val getEventsUseCase: GetEvents,
                                             val getSpeakersUseCase: GetSpeakers,
                                             val getRoomsUseCase: GetRooms,
                                             val eventsMapper: EventMapper,
                                             val speakersMapper: SpeakerMapper,
                                             val roomMapper: RoomMapper) :
        EventListContract.Presenter {

    private var view: EventListContract.View? = null
    private var date: DateTime = DateTime.now()

    override fun onAttach(view: EventListContract.View) {
        this.view = view
        subscribeForChanges()
    }

    private fun subscribeForChanges() {
        conferenceRepository.getEventChanges()
                .subscribeOn(Schedulers.newThread())
                .subscribe { _ ->
                    getEventsUseCase.execute(EventSubscriber(), date.dayOfMonth)
                }
    }

    override fun setDate(conferenceDay: DateTime) {

        this.date = conferenceDay

        getEventsUseCase.execute(EventSubscriber(), date.dayOfMonth)

        getSpeakersUseCase.execute(SpeakersSubscriber())

        getRoomsUseCase.execute(RoomsSubscriber())
    }

    override fun onDetach() {
        this.view = null
        getEventsUseCase.dispose()
    }

    private fun findCurrentSessionIndex(sessions: List<Event>): Int {
        // find the current session index
        val now = DateTime( currentTimeProvider.currentTimeMillis())
        val index =  sessions.indexOfFirst { now.isAfter(it.startTime) && now.isBefore(it.endTime) && (Duration(it.startTime, it.endTime).standardMinutes <= 100L) }
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
    }

    internal fun handleGetSpeakersSuccess(speakers: List<Speaker>) {

        if (speakers.isNotEmpty()) {
            view?.showSpeakers(
                    speakers
                            .map { speakersMapper.mapToView(it) }
                            .associateBy({ it.id }, { it })
            )
        }
    }

    internal fun handleGetRoomsSuccess(rooms: List<Room>) {

        if (rooms.isNotEmpty()) {
            view?.showRooms(
                    rooms
                            .map { roomMapper.mapToView(it) }
                            .associateBy({ it.id }, { it })
            )
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

    inner class SpeakersSubscriber : DisposableSingleObserver<List<Speaker>>() {

        override fun onSuccess(t: List<Speaker>) {
            handleGetSpeakersSuccess(t)
        }

        override fun onError(exception: Throwable) {
            view?.showNoSessions()
        }
    }

    inner class RoomsSubscriber : DisposableSingleObserver<List<Room>>() {

        override fun onSuccess(t: List<Room>) {
            handleGetRoomsSuccess(t)
        }

        override fun onError(exception: Throwable) {
            view?.showNoSessions()
        }
    }
}