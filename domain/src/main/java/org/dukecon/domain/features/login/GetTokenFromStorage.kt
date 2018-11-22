package org.dukecon.domain.features.login

import io.reactivex.Single
import org.dukecon.domain.executor.PostExecutionThread
import org.dukecon.domain.executor.ThreadExecutor
import org.dukecon.domain.features.oauth.TokensStorage
import org.dukecon.domain.interactor.SingleUseCase
import org.dukecon.domain.model.OAuthToken
import javax.inject.Inject

class GetTokenFromStorage @Inject constructor(
    val tokensStorage: TokensStorage,
    threadExecutor: ThreadExecutor,
    postExecutionThread: PostExecutionThread
) : SingleUseCase<OAuthToken, String>(threadExecutor, postExecutionThread) {
    override fun buildUseCaseObservable(params: String?): Single<OAuthToken> {
        return Single.create {
            it.onSuccess(tokensStorage.getToken())
        }
    }
}