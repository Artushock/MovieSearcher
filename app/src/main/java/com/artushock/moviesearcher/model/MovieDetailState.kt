package com.artushock.moviesearcher.model

import com.artushock.moviesearcher.model.dto.MovieDetailDTO

sealed class MovieDetailState {
    data class Success(
        val movie: MovieDetailDTO
    ) : MovieDetailState()

    data class Error(
        val error: Throwable
    ) : MovieDetailState()

    object Loading : MovieDetailState()


}
