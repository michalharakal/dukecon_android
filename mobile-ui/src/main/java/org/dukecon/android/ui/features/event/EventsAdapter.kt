package org.dukecon.android.ui.features.event

import androidx.core.content.ContextCompat
import android.text.format.DateUtils
import android.text.format.DateUtils.formatDateTime
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_session.view.*
import org.dukecon.android.ui.R
import org.dukecon.android.ui.utils.DrawableUtils
import org.dukecon.domain.features.time.CurrentTimeProvider
import org.dukecon.presentation.model.EventView
import org.dukecon.presentation.model.RoomView
import org.dukecon.presentation.model.SpeakerView
import org.joda.time.Duration

internal class EventsAdapter(
    val currentTimeProvider: CurrentTimeProvider,
    val onSessionSelectedListener: ((session: EventView) -> Unit)
) :
        RecyclerView.Adapter<EventsAdapter.ViewHolder>() {

    val sessions: MutableList<EventView> = mutableListOf()
    val speakers: MutableMap<String, SpeakerView> = mutableMapOf()
    val rooms: MutableMap<String, RoomView> = mutableMapOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_session, parent, false)
        return ViewHolder(view, onSessionSelectedListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val context = holder.itemView.context
        val session = sessions[position]

        holder.session = session

        val startTime = formatDateTime(context, session.startTime.millis, DateUtils.FORMAT_SHOW_TIME)
        holder.timeslot.text = String.format(context.getString(R.string.session_start_time), startTime)

        // Dim the session card once hte session is over
        val now = currentTimeProvider.currentTimeMillis()
        if (session.endTime.isAfter(now)) {
            holder.card.setBackgroundColor(ContextCompat.getColor(context, R.color.session_bg))
        } else {
            holder.card.setBackgroundColor(ContextCompat.getColor(context, R.color.session_finished_bg))
        }

        holder.title.text = session.title

        if (speakers.size > 0) {
            val sessionSpeakers = session.speakersId.map { speakers[it.id] }
            if (sessionSpeakers.isEmpty()) {
                holder.speakers.visibility = View.GONE
            } else {
                holder.speakers.visibility = View.VISIBLE
                holder.speakers.text = sessionSpeakers.map { it?.name }.joinToString()
                holder.room.setCompoundDrawablesWithIntrinsicBounds(
                        DrawableUtils.create(context, R.drawable.ic_speaker),
                        null,
                        null,
                        null)
            }
        } else {
            holder.speakers.visibility = View.GONE
        }

        if (session.room.isNotEmpty()) {
            val duration = String.format(context.getString(R.string.session_duration), Duration(session.startTime, session.endTime).standardMinutes)
            holder.room.visibility = View.VISIBLE
            holder.room.text = rooms.get(session.room)?.name + " " + duration
            holder.room.setCompoundDrawablesWithIntrinsicBounds(
                    DrawableUtils.create(context, R.drawable.ic_room),
                    null,
                    null,
                    null)
        } else {
            holder.room.visibility = View.GONE
        }

        if (session.favorite.selected) {
            holder.favorite.visibility = View.VISIBLE
        } else {
            holder.favorite.visibility = View.GONE
        }

        if (position > 0) {
            val previous = sessions[position - 1]
            holder.timeslot.visibility = if (previous.startTime == session.startTime) {
                View.INVISIBLE
            } else {
                View.VISIBLE
            }
        } else {
            holder.timeslot.visibility = View.VISIBLE
        }
    }

    override fun getItemCount(): Int {
        return sessions.size
    }

    internal class ViewHolder(itemView: View, private val onSessionSelectedListener: ((session: EventView) -> Unit)) :
            RecyclerView.ViewHolder(itemView), View.OnClickListener {

        var session: EventView? = null

        val card: CardView
        val timeslot: TextView
        val title: TextView
        val speakers: TextView
        val room: TextView
        val favorite: ImageView

        init {
            card = super.itemView.card
            timeslot = super.itemView.timeslot
            title = super.itemView.title
            speakers = super.itemView.speakers
            room = super.itemView.room
            favorite = super.itemView.favorite
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            if (session != null) {
                onSessionSelectedListener(session!!)
            }
        }
    }
}