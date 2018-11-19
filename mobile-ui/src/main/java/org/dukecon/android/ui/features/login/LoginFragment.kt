package org.dukecon.android.ui.features.login

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_login.*
import org.dukecon.android.ui.R
import org.dukecon.android.ui.ext.getComponent
import org.dukecon.android.ui.features.login.di.LoginComponent
import org.dukecon.android.ui.features.main.MainComponent
import org.dukecon.domain.aspects.auth.AuthManager
import javax.inject.Inject

class LoginFragment : Fragment() {

    @Inject
    lateinit var authManager: AuthManager

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

        login_button.setOnClickListener {
            authManager.login()
        }
    }
}
