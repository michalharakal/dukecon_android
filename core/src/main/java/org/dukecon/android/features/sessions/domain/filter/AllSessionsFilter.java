package org.dukecon.android.features.sessions.domain.filter;

import org.dukecon.android.api.model.Event;
import org.dukecon.android.domain.filter.Filter;

import java.util.ArrayList;
import java.util.List;

public class AllSessionsFilter implements Filter<Event> {
    @Override
    public List<Event> filter(List<Event> events) {
        List<Event> filteredTasks = new ArrayList<>();

        for (Event event : events) {
            filteredTasks.add(event);
        }
        return filteredTasks;

    }
}
