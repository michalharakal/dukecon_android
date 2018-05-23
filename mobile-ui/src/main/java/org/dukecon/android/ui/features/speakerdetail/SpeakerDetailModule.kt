package org.dukecon.android.ui.features.speakerdetail

import dagger.Module
import dagger.Provides
import org.dukecon.domain.features.speaker.GetSpeakerDetailUseCase
import org.dukecon.presentation.feature.speakerdetail.SpeakerDetailContract
import org.dukecon.presentation.feature.speakerdetail.SpeakerDetailPresenter
import org.dukecon.presentation.mapper.SpeakerDetailMapper

@Module
class SpeakerDetailModule {
    @Provides
    fun speakerDetailPresenter(speakerDetailUseCase: GetSpeakerDetailUseCase,
                               speakerDetailMapper: SpeakerDetailMapper): SpeakerDetailContract.Presenter {
        return SpeakerDetailPresenter(speakerDetailUseCase, speakerDetailMapper)
    }
}