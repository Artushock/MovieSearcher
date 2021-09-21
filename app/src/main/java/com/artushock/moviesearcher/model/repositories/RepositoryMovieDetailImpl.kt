package com.artushock.moviesearcher.model.repositories

import com.artushock.moviesearcher.model.MovieDetailDTO
import retrofit2.Callback

class RepositoryMovieDetailImpl(
    private val remoteDataSource: RemoteDataSource
) : RepositoryMovieDetail {
    override fun getMovieDataFromServer(id: Int, callback: Callback<MovieDetailDTO>) {
        remoteDataSource.getMovieDetailDataFromServer(id, callback)
    }

}