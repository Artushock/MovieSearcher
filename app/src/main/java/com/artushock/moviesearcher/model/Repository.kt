package com.artushock.moviesearcher.model

interface Repository {
    fun getMovieListFromLocalStorage(): ArrayList<Movie>
    fun getMovieListFromRemoteStorage(): ArrayList<Movie>
    fun getMoviesByName(text: String): ArrayList<Movie>
}