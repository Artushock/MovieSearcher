package com.artushock.moviesearcher.model

sealed class MovieDetailState {
    data class Success(
        val movie: MovieDetailDTO
    ) : MovieDetailState()

    data class Error(
        val error: Throwable
    ) : MovieDetailState()

    object Loading : MovieDetailState()


}
