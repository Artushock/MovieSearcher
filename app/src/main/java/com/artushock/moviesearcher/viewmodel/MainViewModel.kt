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

class MainViewModel(
    val nowPlayingMoviesToObserve: MutableLiveData<MovieListState> = MutableLiveData<MovieListState>(),
    val popularMoviesToObserve: MutableLiveData<MovieListState> = MutableLiveData<MovieListState>(),
    val topRatedMoviesToObserve: MutableLiveData<MovieListState> = MutableLiveData<MovieListState>(),
    private val repository: RepositoryMovies = RepositoryMoviesImpl(RemoteDataSource())
) : ViewModel() {

    fun getMovies() {
        getMoviesByCategory(nowPlayingMoviesToObserve, MovieCategory.NOW_PLAYING)
        getMoviesByCategory(popularMoviesToObserve, MovieCategory.POPULAR)
        getMoviesByCategory(topRatedMoviesToObserve, MovieCategory.TOP_RATED)
    }

    private fun getMoviesByCategory(
        observe: MutableLiveData<MovieListState>,
        movieCategory: MovieCategory
    ) {

        val callback = object : Callback<MoviesDTO> {
            override fun onResponse(call: Call<MoviesDTO>, response: Response<MoviesDTO>) {
                val serverResponse: MoviesDTO? = response.body()
                observe.postValue(
                    if (response.isSuccessful && serverResponse != null) {
                        MovieListState.Success(
                            serverResponse,
                            movieCategory
                        )
                    } else {
                        MovieListState.Error(Throwable(SERVER_ERROR))
                    }
                )
            }

            override fun onFailure(call: Call<MoviesDTO>, t: Throwable) {
                MovieListState.Error(t)
            }

        }

        when (movieCategory) {
            MovieCategory.NOW_PLAYING -> {
                repository.getNowPlayingMovies(1, callback)
            }
            MovieCategory.TOP_RATED -> {
                repository.getTopRatedMovies(1, callback)
            }
            MovieCategory.POPULAR -> {
                repository.getPopularMovies(1, callback)
            }
        }
    }
}