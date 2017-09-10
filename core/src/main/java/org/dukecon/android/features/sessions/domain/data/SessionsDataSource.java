package org.dukecon.android.features.sessions.domain.data;

import org.dukecon.android.api.model.Event;
import org.joda.time.DateTime;

import java.util.List;

import javax.annotation.Nonnull;

public interface SessionsDataSource {

    interface LoadEventsCallback {

        void onEventsLoaded(List<Event> events);

        void onDataNotAvailable();
    }


    interface LoadEventDatesCallback {

        void onEventDatesLoaded(List<DateTime> dates);

        void onDataNotAvailable();
    }

    interface LoadEventCallback {

        void onEventLoaded(Event event);

        void onDataNotAvailable();
    }

    void refreshEvents();

    void getEvents(@Nonnull LoadEventsCallback callback);

    void getEvent(@Nonnull String taskId, @Nonnull LoadEventCallback callback);

    void getDates(@Nonnull LoadEventDatesCallback callback);
    
}
