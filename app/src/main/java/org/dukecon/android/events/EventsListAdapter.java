package org.dukecon.android.events;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.dukecon.android.R;
import org.dukecon.android.api.model.Event;

import java.util.List;

public class EventsListAdapter extends RecyclerView.Adapter<EventsListAdapter.ViewHolder> {
    private final OnItemClickListener listener;
    private final List<Event> events;
    private Context context;

    public EventsListAdapter(Context context, List<Event> events, OnItemClickListener listener) {
        this.events = events;
        this.listener = listener;
        this.context = context;
    }

    @Override
    public EventsListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_home, null);
        view.setLayoutParams(new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.WRAP_CONTENT));
        return new EventsListAdapter.ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.click(events.get(position), listener);
        holder.name.setText(events.get(position).getTitle());
    }


    @Override
    public int getItemCount() {
        return events.size();
    }


    public interface OnItemClickListener {
        void onClick(Event Item);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name;

        public ViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.name);

        }


        public void click(final Event cityListData, final OnItemClickListener listener) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onClick(cityListData);
                }
            });
        }
    }


}

