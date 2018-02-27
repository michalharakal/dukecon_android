package org.dukecon.presentation.feature.speakerdetail

import io.reactivex.observers.DisposableSingleObserver
import org.dukecon.domain.features.speaker.GetSpeakerDetailUseCase
import org.dukecon.domain.model.Speaker
import org.dukecon.presentation.mapper.SpeakerDetailMapper
import javax.inject.Inject

class SpeakerDetailPresenter @Inject constructor(val speakerDetailUseCase: GetSpeakerDetailUseCase,
                                                 val speakerDetailMapper: SpeakerDetailMapper) :
        SpeakerDetailContract.Presenter {

    private var view: SpeakerDetailContract.View? = null

    override fun onAttach(view: SpeakerDetailContract.View) {
        this.view = view
    }

    override fun onDetach() {
        this.view = null
        speakerDetailUseCase.dispose()

    }

    override fun setSpeakerId(speakerId: String) {
        speakerDetailUseCase.execute(SpeakerDetailsSubscriber(), speakerId)
    }

    private fun handleGetSpeakerSuccess(event: Speaker) {
        this.view?.let {
            it.showSpeaker(speakerDetailMapper.mapToView(event))
        }
    }

    inner class SpeakerDetailsSubscriber : DisposableSingleObserver<Speaker>() {

        override fun onSuccess(t: Speaker) {
            handleGetSpeakerSuccess(t)
        }

        override fun onError(exception: Throwable) {
        }
    }

}
