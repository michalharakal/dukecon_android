package org.dukecon.android.features.sessions;

import android.content.Context;
import android.os.Build;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.annotation.StyleRes;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import org.dukecon.android.R;
import org.dukecon.android.app.DukeconApplication;
import org.dukecon.android.features.sessions.di.SessionDatesModule;
import org.joda.time.DateTime;

import java.util.List;

import javax.inject.Inject;

public class SessionsDateView extends FrameLayout implements SessionDateListContract.View {

    @Inject
    SessionDateListContract.Presenter presenter;

    private SessionsPagerAdapter adapter;
    private ViewPager pager;
    private TabLayout tabs;

    public SessionsDateView(@NonNull Context context) {
        super(context);
        init(context);
    }

    public SessionsDateView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public SessionsDateView(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public SessionsDateView(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr, @StyleRes int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    private void init(Context context) {
        inject(context);

        LayoutInflater.from(context).inflate(R.layout.view_sessions, this, true);

        pager = (ViewPager) findViewById(R.id.pager);
        tabs = (TabLayout) findViewById(R.id.tabs);

        adapter = new SessionsPagerAdapter();
        pager.setAdapter(adapter);
        tabs.setupWithViewPager(pager);
    }

    private void inject(Context context) {
        DukeconApplication.get(context).getMainComponent().plus(new SessionDatesModule(this)).injects(this);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        presenter.onAttach();
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
        adapter.dates.clear();
        adapter.notifyDataSetChanged();
    }

    @Override
    public void showSessionDates(List<DateTime> sessionDates) {
        adapter.dates.clear();
        adapter.dates.addAll(sessionDates);
        adapter.notifyDataSetChanged();
        if (sessionDates.size() >= 1) {
            tabs.setVisibility(View.VISIBLE);
        }
    }
}
