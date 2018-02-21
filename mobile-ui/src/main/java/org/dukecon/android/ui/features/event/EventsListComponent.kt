package org.dukecon.android.ui.features.event

import dagger.Subcomponent

@Subcomponent(modules = arrayOf(EventListModule::class))
interface EventsListComponent {
    fun inject(sessionListView: SessionListView)
    fun inject(eventDateView: EventDateView)
}
