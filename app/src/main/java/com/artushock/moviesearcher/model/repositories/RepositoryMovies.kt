package com.artushock.moviesearcher.model.repositories

import com.artushock.moviesearcher.model.MovieListsLoader
import com.artushock.moviesearcher.model.dto.GenresDTO
import com.artushock.moviesearcher.model.dto.MoviesDTO
import retrofit2.Callback

interface RepositoryMovies {
    fun getNowPlayingMovies(page: Int, callback: Callback<MoviesDTO>)
    fun getPopularMovies(page: Int, callback: Callback<MoviesDTO>)
    fun getTopRatedMovies(page: Int, callback: Callback<MoviesDTO>)
    fun getGenresList(callback: Callback<GenresDTO>)
}