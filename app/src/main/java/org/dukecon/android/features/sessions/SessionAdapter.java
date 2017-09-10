package org.dukecon.android.features.sessions;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import net.danlew.android.joda.DateUtils;

import org.dukecon.android.R;
import org.dukecon.android.api.model.Event;
import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.List;

import static android.text.format.DateUtils.formatDateTime;

public class SessionAdapter extends RecyclerView.Adapter<SessionAdapter.ViewHolder> {

    List<Event> events = new ArrayList<>();

    public void setSessions(List<Event> events) {
        events.clear();
        events.addAll(events);
        notifyDataSetChanged();
    }

    public interface OnEventClickListener {
        void onEventClick(Event event);
    }

    private final OnEventClickListener onEventClickListener;

    public SessionAdapter(OnEventClickListener onEventClickListener) {
        this.onEventClickListener = onEventClickListener;

    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_session, parent, false);
        return new ViewHolder(view, onEventClickListener);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Context context = holder.itemView.getContext();

        Event event = events.get(position);

        holder.event = event;

        String startTime = formatDateTime(context, event.getStart().getMillis(), DateUtils.FORMAT_SHOW_TIME);
        String endTime = formatDateTime(context, event.getEnd().getMillis(), DateUtils.FORMAT_SHOW_TIME);
        holder.timeslot.setText(String.format(context.getString(R.string.session_time), startTime, endTime));

        // Dim the session card once hte session is over
        DateTime now = new DateTime();
        if (now.isBefore(event.getEnd())) {
            holder.card.setBackgroundColor(ContextCompat.getColor(context, R.color.session_bg));
        } else {
            holder.card.setBackgroundColor(ContextCompat.getColor(context, R.color.session_finished_bg));
        }

        holder.title.setText(event.getTitle());
    }

    @Override
    public int getItemCount() {
        return events.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private Event event;
        private final CardView card;
        private final TextView timeslot;
        private final TextView title;
        private final TextView speakers;
        private final TextView room;
        private final ImageView favorite;
        private final OnEventClickListener onEventClickListener;


        public ViewHolder(View itemView, OnEventClickListener onEventClickListener) {
            super(itemView);

            this.onEventClickListener = onEventClickListener;

            this.card = (CardView) super.itemView.findViewById(R.id.card);
            this.timeslot = (TextView) super.itemView.findViewById(R.id.timeslot);
            this.title = (TextView) super.itemView.findViewById(R.id.title);
            this.speakers = (TextView) super.itemView.findViewById(R.id.speakers);
            this.room = (TextView) super.itemView.findViewById(R.id.room);
            this.favorite = (ImageView) super.itemView.findViewById(R.id.favorite);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (onEventClickListener != null) {
                onEventClickListener.onEventClick(event);
            }
        }

        public TextView getTimeslot() {
            return timeslot;
        }
    }
}
