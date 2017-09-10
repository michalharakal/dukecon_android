package org.dukecon.android.features.sessions;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.ViewGroup;

import org.dukecon.android.api.model.Event;
import org.dukecon.android.app.DukeconApplication;
import org.dukecon.android.features.sessions.di.SessionDatesModule;
import org.dukecon.android.features.sessions.di.SessionsListModule;
import org.joda.time.DateTime;

import java.util.List;

import javax.inject.Inject;

public class SessionListView extends RecyclerView implements SessionListContract.View {
    private SessionAdapter adapter;

    @Inject
    SessionNavigator sessionNavigator;

    @Inject
    SessionListContract.Presenter presenter;

    private DateTime date;

    public SessionListView(Context context) {
        super(context);
        init(context);
    }

    public SessionListView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public SessionListView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    private void init(Context context) {
        inject(context);

        this.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        setLayoutManager(new LinearLayoutManager(context, VERTICAL, false));
        addItemDecoration(new SessionItemDecoration(context));
        adapter = new SessionAdapter(new SessionAdapter.OnEventClickListener() {
            @Override
            public void onEventClick(Event event) {
                sessionNavigator.showSession(event);
            }
        });
        super.setAdapter(adapter);
    }

    private void inject(Context context) {
        DukeconApplication.get(context).getMainComponent().plus(new SessionsListModule(this)).injects(this);
    }


    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        presenter.onAttach();
        if (date != null) {
            presenter.setDate(date);
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        presenter.onDetach();
        super.onDetachedFromWindow();
    }

    @Override
    public boolean isActive() {
        return true;
    }

    @Override
    public void onFailure(String appErrorMessage) {

    }

    @Override
    public void setList(List<Event> events) {
        adapter.events.clear();
        adapter.events.addAll(events);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void setDate(DateTime date) {
        this.date = date;
    }

    @Override
    public void showWait() {

    }

    @Override
    public void removeWait() {

    }
}
