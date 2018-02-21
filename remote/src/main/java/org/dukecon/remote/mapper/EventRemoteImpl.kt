package org.dukecon.remote.mapper

import io.reactivex.Single
import org.dukecon.android.api.ConferencesApi
import org.dukecon.data.model.EventEntity
import org.dukecon.data.repository.EventRemote
import javax.inject.Inject


/**
 * Remote implementation for retrieving Event instances. This class implements the
 * [EventRemote] from the Data layer as it is that layers responsibility for defining the
 * operations in which data store implementation layers can carry out.
 */
class EventRemoteImpl @Inject constructor(private val conferenceApi: ConferencesApi,
                                          private val conferenceId: String,
                                          private val entityMapper: EventEntityMapper) :
        EventRemote {

    /**
     * Retrieve a list of [EventEntity] instances from the [ConferencesApi].
     */
    override fun getEvents(): Single<List<EventEntity>> {
        var a = conferenceApi.getEvents(conferenceId)
                        .map { list ->
                            list.map { listItem ->
                                entityMapper.mapFromRemote(listItem)
                            }
                        }

        var b = Single.fromObservable<List<EventEntity>> { }
        return b
    }


}