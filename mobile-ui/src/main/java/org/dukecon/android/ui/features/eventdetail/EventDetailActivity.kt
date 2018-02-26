package org.dukecon.android.ui.features.eventdetail

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_session_detail.*
import org.dukecon.android.ui.R
import org.dukecon.android.ui.ext.getAppComponent
import org.dukecon.android.ui.features.eventdetail.di.EventDetailComponent
import org.dukecon.android.ui.features.eventdetail.di.EventDetailModule

class EventDetailActivity : AppCompatActivity() {
    private lateinit var component: EventDetailComponent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        component = getAppComponent().eventDetailComponent(EventDetailModule())

        setContentView(R.layout.activity_session_detail)

        val sessionId = intent.getStringExtra("session_id")
        session_detail.setSession(sessionId)
    }

    override fun getSystemService(name: String?): Any {
        when (name) {
            "component" -> return component
            else -> return super.getSystemService(name)
        }
    }
}
