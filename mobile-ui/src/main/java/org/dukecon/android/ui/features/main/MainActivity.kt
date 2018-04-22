package org.dukecon.android.ui.features.main

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*
import org.dukecon.android.ui.R
import org.dukecon.android.ui.ext.getAppComponent
import org.dukecon.android.ui.features.event.EventDateView
import org.dukecon.android.ui.features.event.SessionNavigator
import org.dukecon.android.ui.features.eventdetail.EventDetailActivity
import org.dukecon.android.ui.features.info.InfoView
import org.dukecon.android.ui.features.networking.NetworkOfflineChecker
import org.dukecon.android.ui.features.speaker.SpeakerListView
import org.dukecon.android.ui.features.speakerdetail.SpeakerDetailActivity
import org.dukecon.android.ui.features.speakerdetail.SpeakerNavigator
import org.dukecon.presentation.model.EventView
import javax.inject.Inject

class MainActivity : AppCompatActivity(), SessionNavigator, NavigationView.OnNavigationItemSelectedListener, SpeakerNavigator {

    lateinit var component: MainComponent

    @Inject
    lateinit var networkOfflineChecker: NetworkOfflineChecker

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        component = getAppComponent().mainComponent(MainModule(this, this))
        component.inject(this)

        setContentView(R.layout.activity_main)

        setSupportActionBar(toolbar)

        supportActionBar?.let {
            it.setHomeAsUpIndicator(R.drawable.ic_menu)
            it.setDisplayHomeAsUpEnabled(true)
        }

        setTitle(R.string.event_name)

        showView(R.id.action_schedule)

        nav_view.setNavigationItemSelectedListener(this)
        nav_view.setCheckedItem(R.id.action_schedule)
    }

    override fun onResume() {
        super.onResume()
        networkOfflineChecker.enable()
    }

    override fun onPause() {
        super.onPause()
        networkOfflineChecker.disable()
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        if (showView(item.itemId)) {
            drawer_layout.closeDrawers()
            return true
        } else {
            return false
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            android.R.id.home -> {
                drawer_layout.openDrawer(GravityCompat.START)
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun showView(viewId: Int): Boolean {
        val view: View? = when (viewId) {
            R.id.action_schedule -> EventDateView(this)
            R.id.action_speakers -> SpeakerListView(this)
            R.id.action_info -> InfoView(this)
            else -> null
        }
        if (view != null) {
            content.removeAllViews()
            content.addView(view)
            return true
        }
        return false
    }

    override fun getSystemService(name: String?): Any {
        when (name) {
            "component" -> return component
            else -> return super.getSystemService(name)
        }
    }

    override fun showSession(session: EventView) {
        val intent = Intent(this, EventDetailActivity::class.java)
        intent.putExtra("session_id", session.id)
        startActivity(intent)
    }

    override fun navigateToSpeaker(id: String) {
        SpeakerDetailActivity.navigate(this, id)
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

}
