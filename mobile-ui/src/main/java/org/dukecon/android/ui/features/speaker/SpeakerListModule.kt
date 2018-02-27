package org.dukecon.android.ui.features.speaker

import dagger.Module
import dagger.Provides
import org.dukecon.domain.features.event.GetSpeakers
import org.dukecon.presentation.feature.speakers.SpeakerListContract
import org.dukecon.presentation.feature.speakers.SpeakerListPresenter
import org.dukecon.presentation.mapper.SpeakerMapper

@Module
class SpeakerListModule {

    @Provides
    fun speakerListPresenter(getSpeakers: GetSpeakers, speakerMapper: SpeakerMapper): SpeakerListContract.Presenter {
        return SpeakerListPresenter(getSpeakers, speakerMapper)
    }

}