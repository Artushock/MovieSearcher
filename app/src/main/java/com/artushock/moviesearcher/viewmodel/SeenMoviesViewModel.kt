package com.artushock.moviesearcher.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.artushock.moviesearcher.app.App.Companion.getSeenMoviesDao
import com.artushock.moviesearcher.model.MovieCategory
import com.artushock.moviesearcher.model.MovieListState
import com.artushock.moviesearcher.model.SeenMoviesState
import com.artushock.moviesearcher.model.dto.MoviesDTO
import com.artushock.moviesearcher.model.repositories.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SeenMoviesViewModel(
    val seenMoviesToObserve: MutableLiveData<SeenMoviesState> = MutableLiveData<SeenMoviesState>(),
    private val repository: LocalRepository = LocalRepositoryImpl(getSeenMoviesDao())
) : ViewModel(
) {
    fun getSeenMoviesFromDataBase() {
        seenMoviesToObserve.value = SeenMoviesState.Loading
        seenMoviesToObserve.postValue(
            SeenMoviesState.Success(repository.getAllSeenMovies())
        )
    }
}