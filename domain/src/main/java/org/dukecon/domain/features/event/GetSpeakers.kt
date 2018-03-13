package org.dukecon.domain.features.event

import io.reactivex.Single
import org.dukecon.domain.executor.PostExecutionThread
import org.dukecon.domain.executor.ThreadExecutor
import org.dukecon.domain.interactor.SingleUseCase
import org.dukecon.domain.model.Speaker
import org.dukecon.domain.repository.ConferenceRepository
import javax.inject.Inject

/**
 * Use case used for retreiving a [List] of [Speaker] instances from the [ConferenceRepository]
 */
open class GetSpeakers @Inject constructor(val conferenceRepository: ConferenceRepository,
                                           threadExecutor: ThreadExecutor,
                                           postExecutionThread: PostExecutionThread) :
        SingleUseCase<List<Speaker>, Void?>(threadExecutor, postExecutionThread) {
    override fun buildUseCaseObservable(params: Void?): Single<List<Speaker>> {
        return conferenceRepository.getSpeakers()
    }

}