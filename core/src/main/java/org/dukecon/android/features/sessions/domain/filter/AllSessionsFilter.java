package org.dukecon.android.features.sessions.domain.filter;

import org.dukecon.android.api.model.Event;

import java.util.ArrayList;
import java.util.List;

public class AllSessionsFilter implements SessionsFilter {
    @Override
    public List<Event> filter(List<Event> events) {
        List<Event> filteredTasks = new ArrayList<>();

        for (Event event : events) {
            filteredTasks.add(event);
        }
        return filteredTasks;

    }
}
