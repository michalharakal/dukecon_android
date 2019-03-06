package org.dukecon.remote.conference.mapper

import org.dukecon.android.api.model.Favorite
import org.dukecon.data.model.FavoriteEntity
import javax.inject.Inject

class FavoritesEntityMapper @Inject constructor()  {
    fun mapFromRemote(type: Favorite): FavoriteEntity {
        return FavoriteEntity(type.eventId, type.version)
    }

    fun mapToRemote(type: FavoriteEntity ):Favorite  {
        val favorite = Favorite()
        favorite.eventId = type.id
        favorite.version = type.version
        return favorite
    }
}