package com.artushock.moviesearcher.model.repositories

import com.artushock.moviesearcher.model.dto.MovieDetailDTO
import retrofit2.Callback

interface RepositoryMovieDetail {
    fun getMovieDataFromServer(
        id: Int,
        callback: Callback<MovieDetailDTO>
    )
}