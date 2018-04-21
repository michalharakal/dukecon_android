package org.dukecon.domain.features.feedback

import io.reactivex.Single
import org.dukecon.domain.executor.PostExecutionThread
import org.dukecon.domain.executor.ThreadExecutor
import org.dukecon.domain.interactor.SingleUseCase
import org.dukecon.domain.model.Feedback
import org.dukecon.domain.model.Speaker
import org.dukecon.domain.repository.ConferenceRepository
import javax.inject.Inject


/**
 * Use case used for retreiving a single [Speaker] instances from the [ConferenceRepository] with id
 */
open class SubmitFeedbackUseCase @Inject constructor(val conferenceRepository: ConferenceRepository,
                                                     threadExecutor: ThreadExecutor,
                                                     postExecutionThread: PostExecutionThread) :
        SingleUseCase<Any, Feedback>(threadExecutor, postExecutionThread) {
    override fun buildUseCaseObservable(params: Feedback?): Single<Any> {
        return conferenceRepository.submitFeedback(params as Feedback)
    }

}