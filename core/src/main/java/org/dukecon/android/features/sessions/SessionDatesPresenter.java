package org.dukecon.android.features.sessions;

import org.dukecon.android.domain.usecase.UseCase;
import org.dukecon.android.domain.usecase.UseCaseHandler;
import org.dukecon.android.features.sessions.domain.usecase.GetSessionDates;
import org.joda.time.DateTime;

import java.util.List;

public class SessionDatesPresenter implements SessionDateListContract.Presenter {

    private final UseCaseHandler useCaseHandler;
    private final SessionDateListContract.View view;
    private final GetSessionDates getSessionDates;

    public SessionDatesPresenter(UseCaseHandler useCaseHandler, SessionDateListContract.View view, GetSessionDates getSessionDates) {

        this.getSessionDates = getSessionDates;
        this.view = view;
        this.useCaseHandler = useCaseHandler;
    }

    @Override
    public void onAttach() {
        GetSessionDates.RequestValues requestValue = new GetSessionDates.RequestValues();

        useCaseHandler.execute(getSessionDates, requestValue,
                new UseCase.UseCaseCallback<GetSessionDates.ResponseValue>() {
                    @Override
                    public void onSuccess(GetSessionDates.ResponseValue response) {
                        List<DateTime> sessionDates = response.getDates();
                        view.showSessionDates(sessionDates);
                    }

                    @Override
                    public void onError() {
                        view.onFailure("");
                    }
                });

    }

    @Override
    public void onDetach() {

    }
}
