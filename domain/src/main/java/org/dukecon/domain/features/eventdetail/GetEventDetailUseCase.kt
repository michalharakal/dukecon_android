package org.dukecon.domain.features.eventdetail

import io.reactivex.Single
import org.dukecon.domain.executor.PostExecutionThread
import org.dukecon.domain.executor.ThreadExecutor
import org.dukecon.domain.interactor.SingleUseCase
import org.dukecon.domain.model.Event
import org.dukecon.domain.repository.ConferenceRepository
import javax.inject.Inject


/**
 * Use case used for retreiving a single [Event] instances from the [ConferenceRepository] with id
 */
open class GetEventDetailUseCase @Inject constructor(val conferenceRepository: ConferenceRepository,
                                                     threadExecutor: ThreadExecutor,
                                                     postExecutionThread: PostExecutionThread) :
        SingleUseCase<Event, String>(threadExecutor, postExecutionThread) {
    override fun buildUseCaseObservable(params: String?): Single<Event> {
        return conferenceRepository.getEvent(params as String)
    }

}