package com.artushock.moviesearcher.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.artushock.moviesearcher.app.App.Companion.getSeenMoviesDao
import com.artushock.moviesearcher.model.SeenMoviesState
import com.artushock.moviesearcher.model.repositories.*
import kotlinx.coroutines.launch

class SeenMoviesViewModel(
    val seenMoviesToObserve: MutableLiveData<SeenMoviesState> = MutableLiveData<SeenMoviesState>(),
    private val repository: LocalRepository = LocalRepositoryImpl(getSeenMoviesDao())
) : ViewModel(
) {
    fun getSeenMoviesFromDataBase() {
        seenMoviesToObserve.value = SeenMoviesState.Loading
        viewModelScope.launch {
            seenMoviesToObserve.postValue(
                SeenMoviesState.Success(repository.getAllSeenMovies())
            )
        }
    }
}