package org.dukecon.android.ui.features.main

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import io.reactivex.observers.DisposableSingleObserver
import kotlinx.android.synthetic.main.activity_main.*
import org.dukecon.android.ui.R
import org.dukecon.android.ui.ext.getAppComponent
import org.dukecon.android.ui.features.event.ScheduleFragment
import org.dukecon.android.ui.features.event.SessionNavigator
import org.dukecon.android.ui.features.eventdetail.EventDetailActivity
import org.dukecon.android.ui.features.info.InfoFragment
import org.dukecon.android.ui.features.networking.NetworkOfflineChecker
import org.dukecon.android.ui.features.speaker.SpeakersFragment
import org.dukecon.android.ui.features.speakerdetail.SpeakerDetailActivity
import org.dukecon.android.ui.features.speakerdetail.SpeakerNavigator
import org.dukecon.android.ui.utils.consume
import org.dukecon.android.ui.utils.inTransaction
import org.dukecon.domain.aspects.auth.AuthManager
import org.dukecon.domain.features.login.ExchangeCodeForToken
import org.dukecon.presentation.model.EventView
import javax.inject.Inject

class MainActivity : AppCompatActivity(), SessionNavigator,
    SpeakerNavigator {

    companion object {
        private const val FRAGMENT_ID = R.id.content
    }

    lateinit var component: MainComponent

    @Inject
    lateinit var networkOfflineChecker: NetworkOfflineChecker

    @Inject
    lateinit var exchangeCodeForToken: ExchangeCodeForToken


    @Inject
    lateinit var authManager: AuthManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        component = getAppComponent().mainComponent(MainModule(this, this))
        component.inject(this)

        setContentView(R.layout.activity_main)

        setTitle(R.string.event_name)

        navigation.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.action_schedule -> consume {
                    replaceFragment(ScheduleFragment())
                }
                R.id.action_speakers -> consume {
                    // Scroll to current event next time the schedule is opened.
                    replaceFragment(SpeakersFragment())
                }
                R.id.action_info -> consume {
                    // Scroll to current event next time the schedule is opened.
                    replaceFragment(InfoFragment())
                }
                else -> false
            }
        }

        // Add a listener to prevent reselects from being treated as selects.
        navigation.setOnNavigationItemReselectedListener {}

        if (savedInstanceState == null) {
            // Show Schedule on first creation
            navigation.selectedItemId = R.id.action_schedule
        } else {
            // Find the current fragment
            currentFragment =
                supportFragmentManager.findFragmentById(FRAGMENT_ID)
                ?: throw IllegalStateException("Activity recreated, but no fragment found!")
        }

        val uri = intent.data
        if (intent.data != null) {
            exchangeCodeForToken.execute(ExchangeCodeSubscriber(), uri?.getQueryParameter("code") ?: "")
        }

    }

    private fun getCodeFromUri(uri: Uri?): String {
        return uri?.getQueryParameter("code") ?: "XXX"
    }

    override fun onResume() {
        super.onResume()
        networkOfflineChecker.enable()
    }

    override fun onPause() {
        super.onPause()
        networkOfflineChecker.disable()
    }

    private lateinit var currentFragment: Fragment

    private fun <F> replaceFragment(fragment: F) where F : Fragment {
        supportFragmentManager.inTransaction {
            currentFragment = fragment
            replace(FRAGMENT_ID, fragment)
        }
    }

    override fun getSystemService(name: String?): Any {
        return when (name) {
            "component" -> component
            else -> super.getSystemService(name)
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

    inner class ExchangeCodeSubscriber : DisposableSingleObserver<String>() {
        override fun onSuccess(t: String) {
            //mainNavigator.startMain()
        }

        override fun onError(e: Throwable) {
        }
    }
}
