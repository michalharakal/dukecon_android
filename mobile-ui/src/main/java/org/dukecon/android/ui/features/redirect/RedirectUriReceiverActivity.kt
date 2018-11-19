package org.dukecon.android.ui.features.redirect

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import io.reactivex.observers.DisposableSingleObserver
import org.dukecon.android.ui.ext.getAppComponent
import org.dukecon.android.ui.features.eventdetail.di.RedirectUriComponent
import org.dukecon.android.ui.features.eventdetail.di.RedirectUrilModule
import org.dukecon.android.ui.features.login.MainNavigator
import org.dukecon.android.ui.features.main.MainActivity
import org.dukecon.domain.features.login.ExchangeCodeForToken
import javax.inject.Inject

class RedirectUriReceiverActivity : Activity(), MainNavigator {
    override fun startMain() {
        val intent = Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        startActivity(intent);
    }

    @Inject
    lateinit var mainNavigator: MainNavigator

    @Inject
    lateinit var exchangeCodeForToken: ExchangeCodeForToken

    lateinit var component: RedirectUriComponent

    public override fun onCreate(savedInstanceBundle: Bundle?) {
        super.onCreate(savedInstanceBundle)

        component = getAppComponent().redirectComponent(RedirectUrilModule(this))
        component.inject(this)

        val uri = intent.data
        exchangeCodeForToken.execute(ExchangeCodeSubscriber(), uri?.getQueryParameter("code") ?: "")

        finish()
    }

    private fun getCodeFromUri(uri: Uri?): String {
        return uri?.getQueryParameter("code") ?: ""
    }

    inner class ExchangeCodeSubscriber : DisposableSingleObserver<String>() {
        override fun onSuccess(t: String) {
            mainNavigator.startMain()
        }

        override fun onError(e: Throwable) {
        }
    }
}