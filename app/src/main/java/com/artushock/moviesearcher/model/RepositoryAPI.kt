package com.artushock.moviesearcher.model

interface RepositoryAPI {
    fun getNewMovies(listener: MovieLoader.MoviesListener)
    fun getPopularMovies(listener: MovieLoader.MoviesListener)
}