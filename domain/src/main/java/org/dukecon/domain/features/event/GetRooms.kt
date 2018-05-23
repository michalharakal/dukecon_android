package org.dukecon.domain.features.event

import io.reactivex.Single
import org.dukecon.domain.executor.PostExecutionThread
import org.dukecon.domain.executor.ThreadExecutor
import org.dukecon.domain.interactor.SingleUseCase
import org.dukecon.domain.model.Room
import org.dukecon.domain.repository.ConferenceRepository
import javax.inject.Inject

/**
 * Use case used for retreiving a [List] of [Room] instances from the [conferenceRepository]
 */
open class GetRooms @Inject constructor(val conferenceRepository: ConferenceRepository,
                                        threadExecutor: ThreadExecutor,
                                        postExecutionThread: PostExecutionThread) :
        SingleUseCase<List<Room>, Void?>(threadExecutor, postExecutionThread) {
    override fun buildUseCaseObservable(params: Void?): Single<List<Room>> {
        return conferenceRepository.getRooms()
    }

}