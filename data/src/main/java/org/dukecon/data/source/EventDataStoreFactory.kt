package org.dukecon.data.source

import org.dukecon.data.repository.EventCache
import org.dukecon.data.repository.EventDataStore
import javax.inject.Inject


/**
 * Create an instance of a EventDataStore
 */
open class EventDataStoreFactory @Inject constructor(
        private val eventCache: EventCache,
        private val eventCacheDataStore: EventCacheDataStore,
        private val eventRemoteDataStore: EventRemoteDataStore) {

    /**
     * Returns a DataStore based on whether or not there is content in the cache and the cache
     * has not expired
     */
    open fun retrieveDataStore(): EventDataStore {
        if (eventCache.isCached() && !eventCache.isExpired()) {
            return retrieveCacheDataStore()
        }
        return retrieveRemoteDataStore()
    }

    /**
     * Return an instance of the Remote Data Store
     */
    open fun retrieveCacheDataStore(): EventDataStore {
        return eventCacheDataStore
    }

    /**
     * Return an instance of the Cache Data Store
     */
    open fun retrieveRemoteDataStore(): EventDataStore {
        return eventRemoteDataStore
    }

}