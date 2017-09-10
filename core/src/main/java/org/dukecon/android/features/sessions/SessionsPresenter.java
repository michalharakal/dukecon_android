package org.dukecon.android.features.sessions;


import org.dukecon.android.api.model.Event;
import org.dukecon.android.domain.usecase.UseCase;
import org.dukecon.android.domain.usecase.UseCaseHandler;
import org.dukecon.android.features.sessions.domain.filter.DateFilter;
import org.dukecon.android.features.sessions.domain.usecase.GetSessions;
import org.joda.time.DateTime;

import java.util.List;

import javax.annotation.Nonnull;

public class SessionsPresenter implements SessionListContract.Presenter {

    private final SessionListContract.View sessionsView;
    private final GetSessions getSessions;

    private final UseCaseHandler useCaseHandler;

    public SessionsPresenter(@Nonnull UseCaseHandler useCaseHandler,
                             @Nonnull SessionListContract.View sessionsView, @Nonnull GetSessions
                                     getSessions) {

        this.sessionsView = sessionsView;
        this.getSessions = getSessions;
        this.useCaseHandler = useCaseHandler;
    }


    public void loadSessions(final boolean force, DateTime dateTime) {
        if (force) {
            sessionsView.showWait();
        }

        GetSessions.RequestValues requestValue = new GetSessions.RequestValues(force, new DateFilter(dateTime));

        useCaseHandler.execute(getSessions, requestValue,
                new UseCase.UseCaseCallback<GetSessions.ResponseValue>() {
                    @Override
                    public void onSuccess(GetSessions.ResponseValue response) {
                        List<Event> events = response.getEvents();
                        // The view may not be able to handle UI updates anymore
                        if (!sessionsView.isActive()) {
                            return;
                        }
                        if (force) {
                            sessionsView.removeWait();
                        }

                        sessionsView.setList(events);
                    }

                    @Override
                    public void onError() {
                        // The view may not be able to handle UI updates anymore
                        if (!sessionsView.isActive()) {
                            return;
                        }
                        sessionsView.onFailure("error");
                    }
                });

    }

    @Override
    public void setDate(DateTime date) {
        loadSessions(true, date);
    }

    @Override
    public void onAttach() {

    }

    @Override
    public void onDetach() {

    }
}
