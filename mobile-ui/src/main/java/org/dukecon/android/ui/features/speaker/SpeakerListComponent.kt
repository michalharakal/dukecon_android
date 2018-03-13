package org.dukecon.android.ui.features.speaker

import dagger.Subcomponent
import org.dukecon.android.ui.features.speaker.SpeakerListModule
import org.dukecon.android.ui.features.speaker.SpeakerListView

@Subcomponent(modules = arrayOf(SpeakerListModule::class))
interface SpeakerListComponent {
    fun inject(speakerListView: SpeakerListView)
}