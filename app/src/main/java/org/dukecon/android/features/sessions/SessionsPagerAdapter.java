package org.dukecon.android.features.sessions;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import net.danlew.android.joda.DateUtils;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.List;

public class SessionsPagerAdapter extends PagerAdapter {

    List<DateTime> dates = new ArrayList<>();
    Context context;

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        this.context = container.getContext();

        SessionListView listView = new SessionListView(context);
        listView.setDate(dates.get(position));
        container.addView(listView);
        
        return listView;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return DateUtils.formatDateTime(context, dates.get(position), DateUtils.FORMAT_SHOW_DATE);
    }

    @Override
    public int getCount() {
        return dates.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }
}
