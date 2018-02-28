package org.dukecon.android.ui.features.main

import org.dukecon.android.ui.features.event.di.EventsListComponent
import dagger.Subcomponent
import org.dukecon.android.ui.features.speaker.SpeakerListComponent

@Subcomponent(modules = arrayOf(MainModule::class))
interface MainComponent {
    fun sessionListComponent(): EventsListComponent
    fun speakerListComponent(): SpeakerListComponent
//    fun locationComponent(): LocationComponent
}