package com.artushock.moviesearcher.model.repositories

import com.artushock.moviesearcher.BuildConfig
import com.artushock.moviesearcher.model.dto.MovieDetailDTO
import com.artushock.moviesearcher.model.dto.MoviesDTO
import com.google.gson.GsonBuilder
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RemoteDataSource {
    private val movieApi = Retrofit.Builder()
        .baseUrl("https://api.themoviedb.org/")
        .addConverterFactory(
            GsonConverterFactory.create(
                GsonBuilder().setLenient().create()
            )
        )
        .build()
        .create(MovieAPI::class.java)

    fun getMovieDetailDataFromServer(id: Int, callback: Callback<MovieDetailDTO>) {
        movieApi.getMovieById(id, BuildConfig.THEMOVIEDB_API_KEY, "ru-RU").enqueue(callback)
    }

    fun getTopRatedMoviesDataFromServer(page: Int, callback: Callback<MoviesDTO>) {
        movieApi.getTopRatedMovies(BuildConfig.THEMOVIEDB_API_KEY, "ru-RU", page).enqueue(callback)
    }

    fun getNowPlayingMoviesDataFromServer(page: Int, callback: Callback<MoviesDTO>) {
        movieApi.getNowPlayingMovies(BuildConfig.THEMOVIEDB_API_KEY, "ru-RU", page).enqueue(callback)
    }

    fun getPopularMoviesDataFromServer(page: Int, callback: Callback<MoviesDTO>) {
        movieApi.getPopularMovies(BuildConfig.THEMOVIEDB_API_KEY, "ru-RU", page).enqueue(callback)
    }
}