package org.dukecon.android.features.sessions;

import org.dukecon.android.api.model.Event;
import org.dukecon.android.mvp.BasePresenter;
import org.dukecon.android.mvp.BaseView;
import org.joda.time.DateTime;

import java.util.List;

public class SessionListContract {
    public interface View extends BaseView<SessionListContract.Presenter> {

        void onFailure(String appErrorMessage);

        void setList(List<Event> events);

        void setDate(DateTime date);

        void showWait();

        void removeWait();
    }

    public interface Presenter extends BasePresenter<SessionListContract.View> {

        void setDate(DateTime date);
    }
}