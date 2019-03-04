package org.dukecon.android.ui.features.timemachine

import org.dukecon.domain.features.time.CurrentTimeProvider

class ReleaseCurrentTimeProvider : CurrentTimeProvider {
    override fun currentTimeMillis(): Long {
        val instant = Instant.ofEpochMilli(currentTimeMillis())
        return instant.atZone(ZoneId.systemDefault()).toOffsetDateTime()
    }
}
