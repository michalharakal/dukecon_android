package org.dukecon.android.features.sessions;

import org.dukecon.android.mvp.BasePresenter;
import org.dukecon.android.mvp.BaseView;
import org.joda.time.DateTime;

import java.util.List;

public class SessionDateListContract {
    public interface View extends BaseView<SessionDateListContract.Presenter> {

        void onFailure(String appErrorMessage);

        void showSessionDates(List<DateTime> sessionDates);
    }

    public interface Presenter extends BasePresenter<SessionDateListContract.View> {

    }
}
