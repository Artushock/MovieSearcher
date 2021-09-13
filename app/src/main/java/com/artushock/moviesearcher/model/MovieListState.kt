package com.artushock.moviesearcher.model

sealed class MovieListState {
    data class Success(
        val moviesDTO: MoviesDTO,
    ) : MovieListState()

    data class Error(
        val e: Throwable
    ) : MovieListState()

    object Loading : MovieListState()
}
