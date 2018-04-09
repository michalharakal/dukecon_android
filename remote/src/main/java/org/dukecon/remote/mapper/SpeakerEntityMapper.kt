package org.dukecon.remote.mapper

import org.dukecon.data.model.EventEntity
import org.dukecon.data.model.SpeakerEntity
import org.dukecon.data.source.ConferenceConfiguration
import javax.inject.Inject


/**
 * Map a [Speaker] to and from a [EventEntity] instance when data is moving between
 * this later and the Data layer
 */
open class SpeakerEntityMapper @Inject constructor(val conferenceConfiguration: ConferenceConfiguration) : EntityMapper<org.dukecon.android.api.model.Speaker, SpeakerEntity> {

    /**
     * Map an instance of a [org.dukecon.android.api.model.Speaker] to a [SpeakerEntity] model
     */
    override fun mapFromRemote(type: org.dukecon.android.api.model.Speaker): SpeakerEntity {
        return SpeakerEntity(type.id, type.name, type.company
                ?: "", getNormalizedTwitterUrl(type.twitter ?: ""),
                type.bio ?: "", type.website ?: "", getAvatarUrlFromId(type.photoId ?: ""))
    }

    private fun getNormalizedTwitterUrl(twitter: String): String {
        if (twitter.length > 0) {
            if (twitter.startsWith("@")) {
                return "https://twitter.com/${twitter.substring(1)}"
            } else {
                // from https://www.regexplanet.com/advanced/java/index.html
                val twiterRegEx = "((https?://)?(www\\.)?twitter\\.com/)?(@|#!/)?([A-Za-z0-9_]{1,15})(/([-a-z]{1,20}))?".toRegex()
                if (twiterRegEx.matches(twitter)) {
                    return twitter
                } else {
                    return "https://twitter.com/$twitter"
                }
            }
        }
        return twitter
    }

    private fun getAvatarUrlFromId(s: String): String {
        return conferenceConfiguration.speakerAvatarUrl + s
    }

}