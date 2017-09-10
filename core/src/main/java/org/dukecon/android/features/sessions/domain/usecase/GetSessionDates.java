package org.dukecon.android.features.sessions.domain.usecase;

import org.dukecon.android.domain.usecase.UseCase;
import org.dukecon.android.features.sessions.domain.data.SessionsDataSource;
import org.joda.time.DateTime;

import java.util.List;

import javax.annotation.Nonnull;

import static com.google.common.base.Preconditions.checkNotNull;

public class GetSessionDates extends UseCase<GetSessionDates.RequestValues, GetSessionDates.ResponseValue> {

    private final SessionsDataSource sessionsDataSource;

    public GetSessionDates(@Nonnull SessionsDataSource sessionsDataSource) {
        this.sessionsDataSource = checkNotNull(sessionsDataSource, "sessionsDataSource cannot be " +
                "null!");
    }

    @Override
    protected void executeUseCase(final RequestValues values) {
        sessionsDataSource.getDates(new SessionsDataSource.LoadEventDatesCallback() {

            @Override
            public void onEventDatesLoaded(List<DateTime> dates) {
                ResponseValue responseValue = new ResponseValue(dates);
                getUseCaseCallback().onSuccess(responseValue);

            }

            @Override
            public void onDataNotAvailable() {
                getUseCaseCallback().onError();
            }
        });
    }

    public static final class RequestValues implements UseCase.RequestValues {
    }

    public static final class ResponseValue implements UseCase.ResponseValue {

        private final List<DateTime> dates;

        public ResponseValue(@Nonnull List<DateTime> dates) {
            this.dates = dates;
        }

        public List<DateTime> getDates() {
            return dates;
        }
    }
}