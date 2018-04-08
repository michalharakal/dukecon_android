package org.dukecon.android.ui.features.timemachine

import mu.KotlinLogging
import org.joda.time.DateTime

private val logger = KotlinLogging.logger {}

class DebugCurrentTimeProvider : CustomizableCurrentTimeProvider {
    override fun currentTimeMillis(): Long {
        return DateTime().millis + offset
    }

    private var offset: Long = 0

    override fun setCustomMillis(value: Long) {
        logger.info { "Setting custom time to %s".format(DateTime(value).toString("MM/dd/yyyy HH:mm:ss")) }
        offset = value - DateTime().millis
    }
}
