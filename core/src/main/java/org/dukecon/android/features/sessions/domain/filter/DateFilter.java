package org.dukecon.android.features.sessions.domain.filter;

import org.dukecon.android.api.model.Event;
import org.dukecon.android.domain.filter.Filter;
import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;

public class DateFilter implements Filter<Event> {

    private final DateTime dateTimeFilter;

    public DateFilter(@Nonnull final DateTime dateTimeFilter) {
        this.dateTimeFilter = dateTimeFilter;
    }

    @Override
    public List<Event> filter(List<Event> events) {
        List<Event> filteredTasks = new ArrayList<>();

        for (Event event : events) {
            if (dayEquals(dateTimeFilter, event.getStart())) {
                filteredTasks.add(event);
            }
        }
        return filteredTasks;
    }

    private boolean dayEquals(DateTime dateTimeFilter, DateTime start) {
        return ((dateTimeFilter.getDayOfMonth() == start.getDayOfMonth()) &&
                (dateTimeFilter.getMonthOfYear() == start.getMonthOfYear()));
    }
}
