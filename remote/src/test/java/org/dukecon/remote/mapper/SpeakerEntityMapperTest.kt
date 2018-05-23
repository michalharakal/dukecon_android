package org.dukecon.remote.mapper

import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import org.dukecon.android.api.model.Speaker
import org.dukecon.data.source.ConferenceConfiguration
import org.dukecon.domain.aspects.twitter.TwitterLinks
import org.junit.Before
import org.junit.Test

class SpeakerEntityMapperTest {

    private lateinit var conferenceConfiguration: ConferenceConfiguration
    private lateinit var mapper: SpeakerEntityMapper


    @Before
    fun setUp() {
        conferenceConfiguration = mock()
        mapper = SpeakerEntityMapper(conferenceConfiguration, TwitterLinks())
        whenever(conferenceConfiguration.speakerAvatarUrl).thenReturn("https://conference.com/speaker/avatar")
    }

    @Test
    fun mapsEmptyTwitterAsEmptyString() {
        val speaker = Speaker()
        speaker.id = "1234"
        speaker.name = "speaker"
        speaker.twitter = ""
        val speakerEntity = mapper.mapFromRemote(speaker)
        assert(speakerEntity.twitter.equals(""))
    }

    @Test
    fun mapsNullTwitterAsEmptyString() {
        val speaker = Speaker()
        speaker.name = "speaker"
        speaker.id = "1234"
        val speakerEntity = mapper.mapFromRemote(speaker)
        assert(speakerEntity.twitter.equals(""))
    }

    @Test
    fun mapsTwitterHandleToTwitterUrl() {
        val speaker = Speaker()
        speaker.name = "speaker"
        speaker.id = "1234"
        speaker.twitter = "@speaker"
        val speakerEntity = mapper.mapFromRemote(speaker)
        assert(speakerEntity.twitter.equals("https://twitter.com/speaker"))
    }

    @Test
    fun keepsFullTwitteUrl() {
        val speaker = Speaker()
        speaker.name = "speaker"
        speaker.id = "1234"
        speaker.twitter = "https://twitter.com/speaker"
        val speakerEntity = mapper.mapFromRemote(speaker)
        assert(speakerEntity.twitter.equals("https://twitter.com/speaker"))
    }
}