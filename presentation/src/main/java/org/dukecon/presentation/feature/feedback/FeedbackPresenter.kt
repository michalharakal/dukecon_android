package org.dukecon.presentation.feature.feedback

import io.reactivex.observers.DisposableSingleObserver
import org.dukecon.domain.features.feedback.SubmitFeedbackUseCase
import org.dukecon.domain.model.Feedback

class FeedbackPresenter(private val submitFeedbackUseCase: SubmitFeedbackUseCase) : FeedbackMvp.Presenter {
    private var view: FeedbackMvp.View? = null
    private var sessionId: String? = null

    override fun onAttach(view: FeedbackMvp.View) {
        this.view = view
    }

    inner class FeedSubscriber : DisposableSingleObserver<Any>() {
        override fun onSuccess(t: Any) {
            view?.dismiss()
        }

        override fun onError(e: Throwable) {
            view?.dismiss()
        }
    }

    override fun onDetach() {
        this.view = null
    }

    override fun setSessionId(sessionId: String) {
        this.sessionId = sessionId
    }

    override fun submit(overall: Int, comment: String) {
        val sessionId = this.sessionId
        if (sessionId != null) {
            submitFeedbackUseCase.execute(FeedSubscriber(), Feedback(sessionId, overall, comment))
        }
    }
}