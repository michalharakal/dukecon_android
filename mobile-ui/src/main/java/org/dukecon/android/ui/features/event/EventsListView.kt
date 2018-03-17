package org.dukecon.android.ui.features.event

import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.AttributeSet
import android.view.ViewGroup
import org.dukecon.android.ui.ext.getComponent
import org.dukecon.android.ui.features.main.MainComponent
import org.dukecon.domain.features.time.CurrentTimeProvider
import org.dukecon.presentation.feature.event.EventListContract
import org.dukecon.presentation.model.EventView
import org.dukecon.presentation.model.RoomView
import org.dukecon.presentation.model.SpeakerView
import org.joda.time.DateTime
import javax.inject.Inject

class EventsListView(context: Context, attrs: AttributeSet? = null, defStyle: Int = 0) :
        RecyclerView(context, attrs, defStyle), EventListContract.View {

    private val adapter: EventsAdapter
    private var date: DateTime? = null

    @Inject
    lateinit var presenter: EventListContract.Presenter
    @Inject
    lateinit var sessionNavigator: SessionNavigator
    @Inject
    lateinit var currentTimeProvider: CurrentTimeProvider

    init {
        context.getComponent<MainComponent>().sessionListComponent().inject(this)

        layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        layoutManager = LinearLayoutManager(context, VERTICAL, false)
        addItemDecoration(EventItemDecoration(context))
        adapter = EventsAdapter(currentTimeProvider, { session ->
            sessionNavigator.showSession(session)
        })
        super.setAdapter(adapter)
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        presenter.onAttach(this)
        date?.let { presenter.setDate(it) }
    }

    override fun onDetachedFromWindow() {
        presenter.onDetach()
        super.onDetachedFromWindow()
    }

    fun setDate(date: DateTime) {
        this.date = date
    }

    override fun showNoSessions() {
        // todo
    }

    override fun showSessions(sessions: List<EventView>) {
        adapter.sessions.clear()
        adapter.sessions.addAll(sessions)
        adapter.notifyDataSetChanged()
    }

    override fun showSpeakers(speakers: Map<String, SpeakerView>) {
        adapter.speakers.clear()
        adapter.speakers.putAll(speakers)
        adapter.notifyDataSetChanged()
    }

    override fun showRooms(rooms: Map<String, RoomView>) {
        adapter.rooms.clear()
        adapter.rooms.putAll(rooms)
        adapter.notifyDataSetChanged()
    }

    override fun scrollTo(index: Int) {
        scrollToPosition(index)
    }
}

