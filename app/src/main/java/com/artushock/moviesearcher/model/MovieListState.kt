package com.artushock.moviesearcher.model

sealed class MovieListState {
    data class Success(val movieList: ArrayList<Movie>) : MovieListState()
    object Loading : MovieListState()
}
