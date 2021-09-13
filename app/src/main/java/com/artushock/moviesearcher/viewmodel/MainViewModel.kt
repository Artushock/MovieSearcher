package com.artushock.moviesearcher.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.artushock.moviesearcher.model.*

class MainViewModel(
    private val liveDataToObserve: MutableLiveData<MovieListState> = MutableLiveData<MovieListState>(),
    private val repository: RepositoryAPI = RepositoryApiImpl()
) : ViewModel() {

    private val listener: MovieLoader.MoviesListener =
        object : MovieLoader.MoviesListener {
            override fun moviesLoaded(moviesDTO: MoviesDTO) {
                liveDataToObserve.postValue(MovieListState.Success(moviesDTO))
            }

            override fun moviesFailed(e: Throwable) {
                liveDataToObserve.postValue(MovieListState.Error(e))
            }

        }

    fun getLiveData() = liveDataToObserve

    fun getMovieList() {
        liveDataToObserve.value = MovieListState.Loading

        getNewMovieList()
        getPopularMovieList()
    }

    private fun getPopularMovieList() {
        repository.getNewMovies(listener)
    }

    private fun getNewMovieList() {
        repository.getPopularMovies(listener)
    }
}