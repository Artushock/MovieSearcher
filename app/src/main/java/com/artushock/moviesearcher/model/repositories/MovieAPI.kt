package com.artushock.moviesearcher.model.repositories

import com.artushock.moviesearcher.model.MovieDetailDTO
import com.artushock.moviesearcher.model.MoviesDTO
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieAPI {
    @GET("3/movie/{id}?")
    fun getMovieById(
        @Path("id") id: Int,
        @Query("api_key") api_key: String,
        @Query("language") language: String
    ): Call<MovieDetailDTO>

    @GET("3/movie/top_rated?")
    fun getTopRatedMovies(
        @Query("api_key") api_key: String,
        @Query("language") language: String,
        @Query("page") page: Int
    ): Call<MoviesDTO>

    @GET("3/movie/now_playing?")
    fun getNowPlayingMovies(
        @Query("api_key") api_key: String,
        @Query("language") language: String,
        @Query("page") page: Int
    ): Call<MoviesDTO>

    @GET("3/movie/popular?")
    fun getPopularMovies(
        @Query("api_key") api_key: String,
        @Query("language") language: String,
        @Query("page") page: Int
    ): Call<MoviesDTO>
}