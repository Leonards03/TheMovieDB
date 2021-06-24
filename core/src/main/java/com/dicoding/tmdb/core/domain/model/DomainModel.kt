package com.dicoding.tmdb.core.domain.model

import com.dicoding.tmdb.core.data.states.ItemType

sealed class DomainModel(
    var id: Int,
    var type: ItemType
)