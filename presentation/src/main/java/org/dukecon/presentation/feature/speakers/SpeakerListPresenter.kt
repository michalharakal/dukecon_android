package org.dukecon.presentation.feature.speakers

import io.reactivex.observers.DisposableSingleObserver
import org.dukecon.domain.features.event.GetRooms
import org.dukecon.domain.features.event.GetSpeakers
import org.dukecon.domain.model.Speaker
import org.dukecon.presentation.mapper.EventMapper
import org.dukecon.presentation.mapper.SpeakerMapper
import java.util.ArrayList

class SpeakerListPresenter(val getSpeakersUseCase: GetSpeakers,
                           val speakersMapper: SpeakerMapper) : SpeakerListContract.Presenter {

    private var view: SpeakerListContract.View? = null

    override fun onAttach(view: SpeakerListContract.View) {
        this.view = view

        getSpeakersUseCase.execute(SpeakersSubscriber())


        /* speakerProvider.addSpeakerListener(this, { speakers: Map<String, Speaker>? ->
             if (speakers != null) {
                 this.view?.showSpeakers(ArrayList<Speaker>(speakers.values))
             }
         })
         */
    }

    override fun onDetach() {
        this.view = null
    }

    internal fun handleGetSpeakersSuccess(speakers: List<Speaker>) {

        if (speakers.isNotEmpty()) {
            view?.showSpeakers(
                    speakers
                            .map { speakersMapper.mapToView(it) }

            )
        }
    }


    inner class SpeakersSubscriber : DisposableSingleObserver<List<Speaker>>() {

        override fun onSuccess(t: List<Speaker>) {
            handleGetSpeakersSuccess(t)
        }

        override fun onError(exception: Throwable) {
            view?.showNoSpeakers()
        }
    }
}