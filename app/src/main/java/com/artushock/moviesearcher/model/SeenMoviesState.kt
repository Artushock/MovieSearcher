package com.artushock.moviesearcher.model

sealed class SeenMoviesState {
    data class Success(
        val movies: List<Movie>
    ) : SeenMoviesState()

    data class Error(
        val e: Throwable
    ) : SeenMoviesState()

    object Loading : SeenMoviesState()
}