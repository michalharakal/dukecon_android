package org.dukecon.android.ui.features.main.di

import org.dukecon.android.ui.features.event.di.EventsListComponent
import dagger.Subcomponent

@Subcomponent(modules = arrayOf(MainModule::class))
interface MainComponent {
    fun sessionListComponent(): EventsListComponent
//    fun speakerListComponent(): SpeakerListComponent
//    fun locationComponent(): LocationComponent
}