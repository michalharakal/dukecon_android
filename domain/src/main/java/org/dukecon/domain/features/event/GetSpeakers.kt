package org.dukecon.domain.features.event

import io.reactivex.Single
import org.dukecon.domain.executor.PostExecutionThread
import org.dukecon.domain.executor.ThreadExecutor
import org.dukecon.domain.interactor.SingleUseCase
import org.dukecon.domain.model.Speaker
import org.dukecon.domain.repository.EventRepository
import javax.inject.Inject

/**
 * Use case used for retreiving a [List] of [Speaker] instances from the [EventRepository]
 */
open class GetSpeakers @Inject constructor(val eventRepository: EventRepository,
                                         threadExecutor: ThreadExecutor,
                                         postExecutionThread: PostExecutionThread) :
        SingleUseCase<List<Speaker>, Void?>(threadExecutor, postExecutionThread) {
    override fun buildUseCaseObservable(params: Void?): Single<List<Speaker>> {
        return eventRepository.getSpeakers()
    }

}