package com.artushock.moviesearcher.model.repositories

import com.artushock.moviesearcher.model.MovieListsLoader

interface RepositoryMovies {
    fun getNewMovies(listener: MovieListsLoader.MoviesListener)
    fun getPopularMovies(listener: MovieListsLoader.MoviesListener)
    fun getTopRatedMovies(listener: MovieListsLoader.MoviesListener)
}