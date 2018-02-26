package org.dukecon.android.ui.features.event.di

import dagger.Subcomponent
import org.dukecon.android.ui.features.event.EventDateView
import org.dukecon.android.ui.features.event.EventsListView

@Subcomponent(modules = arrayOf(EventListModule::class))
interface EventsListComponent {
    fun inject(eventsListView: EventsListView)
    fun inject(eventDateView: EventDateView)
}
