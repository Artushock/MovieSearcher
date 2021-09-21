package com.artushock.moviesearcher.model

import com.artushock.moviesearcher.model.dto.MoviesDTO

sealed class MovieListState {
    data class Success(
        val moviesDTO: MoviesDTO,
        val movieCategory: MovieCategory
    ) : MovieListState()

    data class Error(
        val e: Throwable
    ) : MovieListState()

    object Loading : MovieListState()
}
