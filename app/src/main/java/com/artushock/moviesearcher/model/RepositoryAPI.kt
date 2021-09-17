package com.artushock.moviesearcher.model

interface RepositoryAPI {
    fun getNewMovies(listener: MovieListsLoader.MoviesListener)
    fun getPopularMovies(listener: MovieListsLoader.MoviesListener)
    fun getTopRatedMovies(listener: MovieListsLoader.MoviesListener)
}