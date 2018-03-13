package org.dukecon.domain.features.eventdetail

import io.reactivex.Single
import org.dukecon.domain.executor.PostExecutionThread
import org.dukecon.domain.executor.ThreadExecutor
import org.dukecon.domain.interactor.SingleUseCase
import org.dukecon.domain.model.Event
import org.dukecon.domain.model.Favorite
import org.dukecon.domain.repository.ConferenceRepository
import javax.inject.Inject


/**
 * Use case used for retreiving a single [Event] instances from the [ConferenceRepository] with id
 */
open class SetFavoriteUseCase @Inject constructor(val conferenceRepository: ConferenceRepository,
                                                  threadExecutor: ThreadExecutor,
                                                  postExecutionThread: PostExecutionThread) :
        SingleUseCase<List<Favorite>, Favorite>(threadExecutor, postExecutionThread) {
    override fun buildUseCaseObservable(params: Favorite?): Single<List<Favorite>> {
        return conferenceRepository.saveFavorite(params as Favorite)
    }
}