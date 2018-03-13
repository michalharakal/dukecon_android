package org.dukecon.domain.repository

import io.reactivex.Single
import org.dukecon.domain.model.Library

interface LibrariesRepository {
    fun getLibraries(): Single<List<Library>>
}