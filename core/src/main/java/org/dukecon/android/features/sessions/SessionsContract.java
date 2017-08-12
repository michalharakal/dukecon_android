package org.dukecon.android.features.sessions;

import org.dukecon.android.mvp.BasePresenter;
import org.dukecon.android.mvp.BaseView;

import org.dukecon.android.api.model.Event;
import org.joda.time.DateTime;

import java.util.List;

public class SessionsContract {
    public interface View extends BaseView<SessionsContract.Presenter> {

        void showWait();

        void removeWait();

        void onFailure(String appErrorMessage);

        void setList(List<Event> events);
    }

    public interface Presenter extends BasePresenter {

        void loadSessions(boolean force, DateTime dateTime);
    }
}