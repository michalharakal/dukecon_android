package org.dukecon.android.ui.app

import android.app.Application
import android.content.Context
import org.dukecon.android.ui.BuildConfig
import org.dukecon.android.ui.injection.ApplicationComponent
import org.dukecon.android.ui.injection.DaggerApplicationComponent
import timber.log.Timber
import android.support.multidex.MultiDex
import android.support.multidex.MultiDexApplication


class DukeconApplication : MultiDexApplication() {

   lateinit var component: ApplicationComponent

    override fun onCreate() {
        super.onCreate()
        component = DaggerApplicationComponent
                .builder()
                .application(this)
                .build()

        component.inject(this)
        setupTimber()
    }

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        MultiDex.install(this)
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
