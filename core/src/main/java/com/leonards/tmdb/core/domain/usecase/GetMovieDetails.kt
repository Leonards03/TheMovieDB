package com.leonards.tmdb.core.domain.usecase

import com.leonards.tmdb.core.domain.repository.Repository
import javax.inject.Inject

class GetMovieDetails @Inject constructor(
    private val repository: Repository
){
    operator fun invoke(id: Int) = repository.fetchMovie(id)
}