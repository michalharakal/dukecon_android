package org.dukecon.domain.features.login

import io.reactivex.Single
import org.dukecon.domain.aspects.auth.AuthManager
import org.dukecon.domain.executor.PostExecutionThread
import org.dukecon.domain.executor.ThreadExecutor
import org.dukecon.domain.interactor.SingleUseCase
import javax.inject.Inject

class ExchangeCodeForToken @Inject constructor(
    val oauthManager: AuthManager,
    threadExecutor: ThreadExecutor,
    postExecutionThread: PostExecutionThread
) : SingleUseCase<String, String>(threadExecutor, postExecutionThread) {
    override fun buildUseCaseObservable(params: String?): Single<String> {
        return Single.create {
            oauthManager.exchangeToken(params!!)
        }
    }
}