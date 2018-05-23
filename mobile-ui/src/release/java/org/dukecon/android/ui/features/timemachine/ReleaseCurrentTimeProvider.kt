package org.dukecon.android.ui.features.timemachine

import org.dukecon.domain.features.time.CurrentTimeProvider
import org.joda.time.DateTime

class ReleaseCurrentTimeProvider : CurrentTimeProvider {
    override fun currentTimeMillis(): Long {
        return DateTime().millis
    }
}
