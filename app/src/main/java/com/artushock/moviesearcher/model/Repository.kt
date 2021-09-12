package com.artushock.moviesearcher.model

interface Repository {
    fun getMovieList(): ArrayList<Movie>
    fun getMoviesByName(text: String): ArrayList<Movie>
}