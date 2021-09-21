package com.artushock.moviesearcher.model.repositories

import com.artushock.moviesearcher.model.MovieDetailDTO
import retrofit2.Callback

interface RepositoryMovieDetail {
    fun getMovieDataFromServer(
        id: Int,
        callback: Callback<MovieDetailDTO>
    )
}