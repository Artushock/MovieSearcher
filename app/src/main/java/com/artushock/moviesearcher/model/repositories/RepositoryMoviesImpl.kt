package com.artushock.moviesearcher.model.repositories

import com.artushock.moviesearcher.model.dto.GenresDTO
import com.artushock.moviesearcher.model.dto.MoviesDTO
import retrofit2.Callback

class RepositoryMoviesImpl(
    private val remoteDataSource: RemoteDataSource
) : RepositoryMovies {

    override fun getNowPlayingMovies(page: Int, callback: Callback<MoviesDTO>) {
        remoteDataSource.getNowPlayingMoviesDataFromServer(page, callback)
    }

    override fun getPopularMovies(page: Int, callback: Callback<MoviesDTO>) {
        remoteDataSource.getPopularMoviesDataFromServer(page, callback)
    }

    override fun getTopRatedMovies(page: Int, callback: Callback<MoviesDTO>) {
        remoteDataSource.getTopRatedMoviesDataFromServer(page, callback)
    }

}