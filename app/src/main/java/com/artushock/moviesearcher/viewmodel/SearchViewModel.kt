package com.artushock.moviesearcher.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.artushock.moviesearcher.model.MovieCategory
import com.artushock.moviesearcher.model.MovieListState
import com.artushock.moviesearcher.model.dto.MoviesDTO
import com.artushock.moviesearcher.model.repositories.RemoteDataSource
import com.artushock.moviesearcher.model.repositories.RepositoryMovies
import com.artushock.moviesearcher.model.repositories.RepositoryMoviesImpl
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchViewModel(
    val topRatedMoviesToObserve: MutableLiveData<MovieListState> = MutableLiveData<MovieListState>(),
    private val repository: RepositoryMovies = RepositoryMoviesImpl(RemoteDataSource())
) : ViewModel(
) {
    fun getTopRatedMovies() {
        val callback = object : Callback<MoviesDTO> {
            override fun onResponse(call: Call<MoviesDTO>, response: Response<MoviesDTO>) {
                val serverResponse: MoviesDTO? = response.body()
                topRatedMoviesToObserve.postValue(
                    if (response.isSuccessful && serverResponse != null) {
                        MovieListState.Success(serverResponse, MovieCategory.TOP_RATED)
                    } else {
                        MovieListState.Error(Throwable(SERVER_ERROR))
                    }
                )
            }

            override fun onFailure(call: Call<MoviesDTO>, t: Throwable) {
                MovieListState.Error(t)
            }

        }
        repository.getPopularMovies(1, callback)
    }
}