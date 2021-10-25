package com.leonards.tmdb.core.domain.usecase

import com.leonards.tmdb.core.domain.repository.Repository
import javax.inject.Inject

class FetchTvShows @Inject  constructor(
    private val repository: Repository
){
    operator fun invoke() = repository.fetchTvShows()
}