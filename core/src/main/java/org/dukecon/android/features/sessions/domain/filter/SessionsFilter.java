package org.dukecon.android.features.sessions.domain.filter;

import org.dukecon.android.api.model.Event;

import java.util.List;

public interface SessionsFilter {
    List<Event> filter(List<Event> events);
}
