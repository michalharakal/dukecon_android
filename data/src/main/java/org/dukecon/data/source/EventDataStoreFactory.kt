package org.dukecon.data.source

import org.dukecon.data.repository.ConferenceDataCache
import org.dukecon.data.repository.EventDataStore
import org.dukecon.domain.features.networking.NetworkUtils
import javax.inject.Inject


open class EventDataStoreFactory @Inject constructor(
        private val conferenceDataCache: ConferenceDataCache,
        private val eventCacheDataStore: EventCacheDataStore,
        private val eventRemoteDataStore: EventRemoteDataStore,
        private val networkUtils: NetworkUtils) {

    /**
     * Returns a DataStore based on whether or not there is content in the cache and the cache
     * has not expired
     */
    open fun retrieveDataStore(): EventDataStore {
        if (networkUtils.isInternetConected) {
            if (conferenceDataCache.isCached() && !conferenceDataCache.isExpired()) {
                return retrieveCacheDataStore()
            }
        } else {
            // ignore data expiration, if device has cache and no internet
            if (conferenceDataCache.isCached()) {
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