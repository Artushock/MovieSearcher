package com.artushock.moviesearcher.model

sealed class MovieListState {
    data class SuccessLocal(val movieList: ArrayList<Movie>) : MovieListState()
    data class SuccessRemote(val movieList: ArrayList<Movie>) : MovieListState()
    object Error : MovieListState()
    object Loading : MovieListState()
}
