package org.dukecon.android.ui.features.event

import android.content.Context
import android.text.format.DateUtils
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter
import org.joda.time.DateTime

internal class SessionPagerAdapter : PagerAdapter() {
    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }

    var dates: List<DateTime> = arrayListOf()
    var context: Context? = null

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        context = container.context
        val v = EventsListView(container.context)
        v.setDate(dates[position])
        container.addView(v)
        return v
    }

    override fun getPageTitle(position: Int): CharSequence {
        return DateUtils.formatDateTime(context, dates[position].millis, DateUtils.FORMAT_SHOW_DATE)
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }

    override fun getCount(): Int {
        return dates.size
    }

    fun clear() {
        dates = arrayListOf()
    }

    fun showEventDates(eventDate: List<DateTime>) {
        dates = eventDate
    }
}