package org.dukecon.android.ui.app

import android.app.Application
import android.content.Context
import com.crashlytics.android.Crashlytics
import org.dukecon.android.ui.BuildConfig
import org.dukecon.android.ui.injection.ApplicationComponent
import org.dukecon.android.ui.injection.DaggerApplicationComponent
import timber.log.Timber
import io.reactivex.plugins.RxJavaPlugins
import androidx.multidex.MultiDex

class DukeconApplication : Application() {

    lateinit var component: ApplicationComponent

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }

    override fun onCreate() {
        super.onCreate()
        component = DaggerApplicationComponent
            .builder()
            .application(this)
            .build()

        component.inject(this)
        setupTimber()
        setupRxJava()
    }

    private fun setupRxJava() {
        RxJavaPlugins.setErrorHandler { e -> Crashlytics.logException(e); }
    }

    private fun setupTimber() {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }

    override fun getSystemService(name: String?): Any {
        when (name) {
            "component" -> return component
            else -> return super.getSystemService(name)
        }
    }
}
