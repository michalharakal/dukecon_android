package org.dukecon.android.ui.features.main

import org.dukecon.android.ui.features.event.EventsListComponent
import dagger.Subcomponent

@Subcomponent(modules = arrayOf(MainModule::class))
interface MainComponent {
    fun sessionListComponent(): EventsListComponent
//    fun speakerListComponent(): SpeakerListComponent
//    fun locationComponent(): LocationComponent
}