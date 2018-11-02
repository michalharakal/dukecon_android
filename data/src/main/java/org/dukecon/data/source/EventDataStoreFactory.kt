package org.dukecon.data.source

import org.dukecon.data.repository.EventDataStore
import javax.inject.Inject

open class EventDataStoreFactory @Inject constructor(
    private val eventCacheDataStore: EventCacheDataStore,
    private val eventRemoteDataStore: EventRemoteDataStore
) {

    /**
     * Returns a DataStore based on whether or not there is content in the cache and the cache
     * has not expired
     */
    open fun retrieveDataStore(isCached: Boolean, isExpired: Boolean, hasInternetConnected: Boolean): EventDataStore {
        if (hasInternetConnected) {
            if (isCached && !isExpired) {
                return retrieveCacheDataStore()
            }
        } else {
            if (isCached) {
                return retrieveCacheDataStore()
            }
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