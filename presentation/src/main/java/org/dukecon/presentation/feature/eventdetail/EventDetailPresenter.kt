package org.dukecon.presentation.feature.eventdetail

import io.reactivex.observers.DisposableSingleObserver
import org.dukecon.domain.aspects.auth.AuthManager
import org.dukecon.domain.features.eventdetail.GetEventDetailUseCase
import org.dukecon.domain.features.eventdetail.SetFavoriteUseCase
import org.dukecon.domain.features.login.GetTokenFromStorage
import org.dukecon.domain.model.Event
import org.dukecon.domain.model.Favorite
import org.dukecon.domain.model.OAuthToken
import org.dukecon.presentation.mapper.EventMapper
import org.dukecon.presentation.mapper.SpeakerMapper
import javax.inject.Inject

class EventDetailPresenter @Inject constructor(
    val eventDetailUseCase: GetEventDetailUseCase,
    val setFavoriteUseCase: SetFavoriteUseCase,
    val speakerMapper: SpeakerMapper,
    val eventsMapper: EventMapper,
    val getTokenFromStorage: GetTokenFromStorage,
    val authManager: AuthManager
) : EventDetailContract.Presenter {

    private var view: EventDetailContract.View? = null

    override fun onAttach(view: EventDetailContract.View) {
        this.view = view
        getTokenFromStorage.execute(GetTokenFromStorageObserver())
    }

    inner class GetTokenFromStorageObserver : DisposableSingleObserver<OAuthToken>() {
        override fun onSuccess(t: OAuthToken) {
            view?.setHasSession(authManager.hasSession(t))
        }

        override fun onError(e: Throwable) {
            view?.setHasSession(false)
        }
    }

    override fun onDetach() {
        this.view = null
        eventDetailUseCase.dispose()
        setFavoriteUseCase.dispose()
    }

    private var currentFavouriteStatus: Boolean = false

    override fun toggleFavorite() {
        setFavoriteUseCase.execute(SetFavoriteSubscriber(), Favorite(sessionId, !currentFavouriteStatus))
    }

    private lateinit var sessionId: String

    override fun setSessionId(sessionId: String) {
        this.sessionId = sessionId
        eventDetailUseCase.execute(EventDetailsSubscriber(), sessionId)
    }

    private fun handleGetEventSuccess(event: Event) {
        this.view?.let {
            it.showSessionDetail(eventsMapper.mapToView(event))
            it.showSpeakerInfo(event.speakers.map { speaker -> speakerMapper.mapToView(speaker) })
            this.currentFavouriteStatus = event.favorite.selected
            it.setIsFavorite(event.favorite.selected)
        }
    }

    private fun handleSetFavoriteSuccess(t: List<Favorite>) {
        val found = t.find { it.id.equals(sessionId) }
        if (found != null) {
            this.currentFavouriteStatus = found.selected
            view?.setIsFavorite(found.selected)
        } else {
            this.currentFavouriteStatus = false
            view?.setIsFavorite(false)
        }
    }

    inner class EventDetailsSubscriber : DisposableSingleObserver<Event>() {

        override fun onSuccess(t: Event) {
            handleGetEventSuccess(t)
        }

        override fun onError(exception: Throwable) {
            view?.showNoEvent()
        }
    }

    inner class SetFavoriteSubscriber : DisposableSingleObserver<List<Favorite>>() {

        override fun onSuccess(t: List<Favorite>) {
            handleSetFavoriteSuccess(t)
        }

        override fun onError(exception: Throwable) {
        }
    }
}