package org.dukecon.android.cache

import io.reactivex.Completable
import io.reactivex.Single
import org.dukecon.android.cache.mapper.EventEntityMapper
import org.dukecon.data.mapper.EventMapper
import org.dukecon.data.model.EventEntity
import org.dukecon.data.repository.EventCache
import javax.inject.Inject

/**
 * Cached implementation for retrieving and saving Event instances. This class implements the
 * [EventCache] from the Data layer as it is that layers responsibility for defining the
 * operations in which data store implementation layers can carry out.
 */
class EventCacheImpl @Inject constructor(private val entityMapper: EventEntityMapper,
                                         private val mapper: EventMapper) :
        EventCache {
    override fun clearEvents(): Completable {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun saveEvents(events: List<EventEntity>): Completable {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getEvents(): Single<List<EventEntity>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun isCached(): Boolean {
       return false;
    }

    override fun setLastCacheTime(lastCache: Long) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun isExpired(): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}