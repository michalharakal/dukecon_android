package org.dukecon.android.cache.model

import org.joda.time.DateTime


/**
 * Model used solely for the caching of a event
 */
data class CachedEvent(val name: String, val title: String, val avatar: String, val startTime: DateTime,
                       val endTime: DateTime)