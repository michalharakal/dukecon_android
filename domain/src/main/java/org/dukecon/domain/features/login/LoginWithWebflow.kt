package org.dukecon.domain.features.login

import io.reactivex.Single
import org.dukecon.domain.aspects.auth.AuthManager
import org.dukecon.domain.executor.PostExecutionThread
import org.dukecon.domain.executor.ThreadExecutor
import org.dukecon.domain.interactor.SingleUseCase
import org.dukecon.domain.model.UserLogin
import javax.inject.Inject

class LoginWithWebflow @Inject constructor(
    private val authManager: AuthManager,
    threadExecutor: ThreadExecutor,
    postExecutionThread: PostExecutionThread
) : SingleUseCase<String, UserLogin>(threadExecutor, postExecutionThread) {
    override fun buildUseCaseObservable(params: UserLogin?): Single<String> {
        return Single.create {
            authManager.login()
        }
    }
}