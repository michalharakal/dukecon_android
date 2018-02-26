package org.dukecon.domain.features.event

import io.reactivex.Single
import org.dukecon.domain.executor.PostExecutionThread
import org.dukecon.domain.executor.ThreadExecutor
import org.dukecon.domain.interactor.SingleUseCase
import org.dukecon.domain.model.Event
import org.dukecon.domain.repository.EventRepository
import org.joda.time.DateTime
import javax.inject.Inject

/**
 * Use case used for retreiving a [List] of [Event] instances from the [EventRepository]
 */
open class GetEventDates @Inject constructor(val eventRepository: EventRepository,
                                             threadExecutor: ThreadExecutor,
                                             postExecutionThread: PostExecutionThread) :
        SingleUseCase<List<DateTime>, Void?>(threadExecutor, postExecutionThread) {

    public override fun buildUseCaseObservable(params: Void?): Single<List<DateTime>> {
        return eventRepository.getEventDates()
    }
}