package org.dukecon.android.cache.model

import org.threeten.bp.OffsetDateTime

data class CachedEvent(
        val name: String,
        val title: String,
        val avatar: String,
        val startTime: OffsetDateTime,
        val endTime: OffsetDateTime,
        val speakerIds: List<String>,
        val roomId: String)