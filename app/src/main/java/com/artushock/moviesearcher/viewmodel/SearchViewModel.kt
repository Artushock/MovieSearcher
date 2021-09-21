package com.artushock.moviesearcher.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.artushock.moviesearcher.model.*
import com.artushock.moviesearcher.model.repositories.RepositoryMovies
import com.artushock.moviesearcher.model.repositories.RepositoryApiImpl

class SearchViewModel(
    private val moviesToObserve: MutableLiveData<MovieListState> = MutableLiveData<MovieListState>(),
    private val repository: RepositoryMovies = RepositoryApiImpl()
) : ViewModel(
) {
    private val moviesListener: MovieListsLoader.MoviesListener =
        object : MovieListsLoader.MoviesListener {
            override fun moviesLoaded(moviesDTO: MoviesDTO, movieCategory: MovieCategory) {
                moviesToObserve.postValue(MovieListState.Success(moviesDTO, movieCategory))
            }

            override fun moviesLoadingFailed(e: Throwable) {
                moviesToObserve.postValue(MovieListState.Error(e))
            }
        }

    fun getMoviesLiveData() = moviesToObserve

    fun getPopularMovieList() {
        repository.getPopularMovies(moviesListener)
    }

    fun findMovie(string: String) {

    }
}