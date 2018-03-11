package org.dukecon.domain.model

enum class DataChange {
    ANY, SPEAKER, EVENT, FAVORITE
}

data class Change(val dataChange: DataChange)