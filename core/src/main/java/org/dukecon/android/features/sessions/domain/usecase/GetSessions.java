package org.dukecon.android.features.sessions.domain.usecase;

import org.dukecon.android.api.model.Event;
import org.dukecon.android.domain.filter.Filter;
import org.dukecon.android.domain.usecase.UseCase;
import org.dukecon.android.features.sessions.domain.data.SessionsDataSource;

import java.util.List;

import javax.annotation.Nonnull;

import static com.google.common.base.Preconditions.checkNotNull;

public class GetSessions extends UseCase<GetSessions.RequestValues, GetSessions.ResponseValue> {

    private final SessionsDataSource sessionsDataSource;

    public GetSessions(@Nonnull SessionsDataSource sessionsDataSource) {
        this.sessionsDataSource = checkNotNull(sessionsDataSource, "sessionsDataSource cannot be " +
                "null!");
    }

    @Override
    protected void executeUseCase(final RequestValues values) {
        if (values.isForceUpdate()) {
            sessionsDataSource.refreshEvents();
        }
        sessionsDataSource.getEvents(new SessionsDataSource.LoadEventsCallback() {
            @Override
            public void onEventsLoaded(List<Event> events) {
                Filter sessionsFilter = values.getSessionsFilter();
                List<Event> eventsFiltered = sessionsFilter.filter(events);
                ResponseValue responseValue = new ResponseValue(eventsFiltered);
                getUseCaseCallback().onSuccess(responseValue);
            }

            @Override
            public void onDataNotAvailable() {
                getUseCaseCallback().onError();
            }
        });
    }

    public static final class RequestValues implements UseCase.RequestValues {

        private final Filter<Event> sessionsFilter;
        private final boolean forceUpdate;

        public RequestValues(boolean forceUpdate, @Nonnull Filter<Event> sessionsFilter) {
            this.sessionsFilter = checkNotNull(sessionsFilter, "currentFiltering cannot be null!");
            this.forceUpdate = forceUpdate;
        }

        public boolean isForceUpdate() {
            return forceUpdate;
        }

        public Filter<Event> getSessionsFilter() {
            return sessionsFilter;
        }
    }

    public static final class ResponseValue implements UseCase.ResponseValue {

        private final List<Event> events;

        public ResponseValue(@Nonnull List<Event> events) {
            this.events = checkNotNull(events, "tasks cannot be null!");
        }

        public List<Event> getEvents() {
            return events;
        }
    }
}

