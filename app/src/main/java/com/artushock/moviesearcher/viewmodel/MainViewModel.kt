package com.artushock.moviesearcher.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.artushock.moviesearcher.model.Movie
import com.artushock.moviesearcher.model.MovieListState
import com.artushock.moviesearcher.model.Repository
import com.artushock.moviesearcher.model.RepositoryImpl

class MainViewModel(
    private val liveDataToObserve: MutableLiveData<MovieListState> = MutableLiveData<MovieListState>(),
    private val repository: Repository = RepositoryImpl()
) : ViewModel() {

    fun getLiveData() = liveDataToObserve
    fun getMovieListFromLocalStorage() = getList()

    private fun getList(){
        liveDataToObserve.value = MovieListState.Loading
        Thread{
            Thread.sleep(3000)
            liveDataToObserve.postValue(MovieListState.Success(repository.getMovieListFromLocalStorage()))
        }.start()
    }


}