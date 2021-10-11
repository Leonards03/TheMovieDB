package com.leonards.tmdb.core.domain.model

import com.leonards.tmdb.core.data.states.ItemType

sealed class DomainModel(
    var id: Int,
    var type: ItemType
)