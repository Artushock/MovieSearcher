package com.artushock.moviesearcher.model

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
