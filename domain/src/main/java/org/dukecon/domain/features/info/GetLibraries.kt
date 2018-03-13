package org.dukecon.domain.features.info

import io.reactivex.Single
import org.dukecon.domain.executor.PostExecutionThread
import org.dukecon.domain.executor.ThreadExecutor
import org.dukecon.domain.interactor.SingleUseCase
import org.dukecon.domain.model.Library
import org.dukecon.domain.repository.LibrariesRepository
import javax.inject.Inject


/**
 * Use case used for retreiving a [List] of [Library] instances from the [LibrariesRepository]
 */
open class GetLibrariesUseCase @Inject constructor(val librariesRepository: LibrariesRepository,
                                                   threadExecutor: ThreadExecutor,
                                                   postExecutionThread: PostExecutionThread) :
        SingleUseCase<List<Library>, Void?>(threadExecutor, postExecutionThread) {
    override fun buildUseCaseObservable(params: Void?): Single<List<Library>> {
        return librariesRepository.getLibraries()
    }
}