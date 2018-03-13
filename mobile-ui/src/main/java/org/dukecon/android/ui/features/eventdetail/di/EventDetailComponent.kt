package org.dukecon.android.ui.features.eventdetail.di

import org.dukecon.android.ui.features.eventdetail.EventDetailView
import dagger.Subcomponent


@Subcomponent(modules = arrayOf(EventDetailModule::class))
interface EventDetailComponent {
    fun inject(eventDetailView: EventDetailView)
}
