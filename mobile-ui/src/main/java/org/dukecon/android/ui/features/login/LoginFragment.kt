package org.dukecon.android.ui.features.login

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import io.reactivex.observers.DisposableSingleObserver
import kotlinx.android.synthetic.main.fragment_login.*
import org.dukecon.android.ui.R
import org.dukecon.android.ui.ext.getComponent
import org.dukecon.android.ui.features.main.MainComponent
import org.dukecon.domain.aspects.auth.AuthManager
import org.dukecon.domain.features.login.CleanTokenFromStorage
import org.dukecon.domain.features.login.GetTokenFromStorage
import org.dukecon.domain.model.OAuthToken
import javax.inject.Inject

class LoginFragment : Fragment() {

    @Inject
    lateinit var authManager: AuthManager

    @Inject
    lateinit var cleanTokenFromStorage: CleanTokenFromStorage

    @Inject
    lateinit var getTokenFromStorage: GetTokenFromStorage

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        context!!.getComponent<MainComponent>().loginComponent().inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getTokenFromStorage.execute(GetTokenObserver())

        login_button.visibility = INVISIBLE

        login_button.setOnClickListener {
            if (hasSession) {
                cleanTokenFromStorage.execute(CleanTokenFromStorageObserver())
            } else {
                authManager.login(activity as Object)
            }
        }
    }

    private var hasSession: Boolean = false;

    inner class GetTokenObserver : DisposableSingleObserver<OAuthToken>() {
        override fun onSuccess(token: OAuthToken) {
            login_button.visibility = VISIBLE
            hasSession = authManager.hasSession(token)
            login_button.text = context?.getText(if (hasSession) R.string.logout else R.string.login)
        }

        override fun onError(e: Throwable) {
            login_button.visibility = VISIBLE
            hasSession = false;
            login_button.text = context?.getText(R.string.login)
        }
    }

    inner class CleanTokenFromStorageObserver : DisposableSingleObserver<Any>() {
        override fun onSuccess(t: Any) {
            hasSession = false;
            login_button.text = context?.getText(R.string.login)
        }

        override fun onError(e: Throwable) {
            login_button.text = context?.getText(R.string.login)
        }
    }
}

