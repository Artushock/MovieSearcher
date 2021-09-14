package com.artushock.moviesearcher.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.artushock.moviesearcher.model.MovieListState

class SearchViewModel(
    private val liveDataToObserve: MutableLiveData<MovieListState> = MutableLiveData<MovieListState>(),
    //private val repository: Repository = RepositoryImpl()
) : ViewModel(
) {
   /* fun getLiveData() = liveDataToObserve
    fun getMovieList() = getList()

    private fun getList() {
        liveDataToObserve.value = MovieListState.Loading
        Thread {
            Thread.sleep(1000)
            liveDataToObserve.postValue(MovieListState.Success(repository.getMovieList()))
        }.start()
    }

    fun findMovie(text: String) {
        liveDataToObserve.value = MovieListState.Loading
        Thread {
            Thread.sleep(1000)
            liveDataToObserve.postValue(MovieListState.Success(repository.getMoviesByName(text)))
        }.start()
    }*/
}