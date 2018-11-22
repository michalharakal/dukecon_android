package org.dukecon.domain.features.login

import io.reactivex.Single
import org.dukecon.domain.executor.PostExecutionThread
import org.dukecon.domain.executor.ThreadExecutor
import org.dukecon.domain.features.oauth.TokensStorage
import org.dukecon.domain.interactor.SingleUseCase
import javax.inject.Inject

class CleanTokenFromStorage @Inject constructor(
    val tokensStorage: TokensStorage,
    threadExecutor: ThreadExecutor,
    postExecutionThread: PostExecutionThread
) : SingleUseCase<Any, String>(threadExecutor, postExecutionThread) {
    override fun buildUseCaseObservable(params: String?): Single<Any> {
        return Single.create {
            it.onSuccess(tokensStorage.clear())
        }
    }
}
