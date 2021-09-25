package com.artushock.moviesearcher.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.artushock.moviesearcher.app.App.Companion.getSeenMoviesDao
import com.artushock.moviesearcher.model.Movie
import com.artushock.moviesearcher.model.MovieDetailState
import com.artushock.moviesearcher.model.dto.MovieDetailDTO
import com.artushock.moviesearcher.model.repositories.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

const val SERVER_ERROR = "SERVER_ERROR"
const val CORRUPTED_DATA = "CORRUPTED_DATA"

class DetailViewModel(
    val movieDetailLiveData: MutableLiveData<MovieDetailState> = MutableLiveData(),
    private val remoteRepository: RepositoryMovieDetail = RepositoryMovieDetailImpl(RemoteDataSource()),
    private val seenMoviesRepository: LocalRepository = LocalRepositoryImpl(getSeenMoviesDao())
) : ViewModel() {

    fun getDetailLiveDataFromServer(id: Int) {
        movieDetailLiveData.value = MovieDetailState.Loading
        remoteRepository.getMovieDataFromServer(id, callback)
    }

    fun saveSeenMovieToDataBase(movie: Movie) {
        seenMoviesRepository.saveEntity(movie)
    }

    private val callback = object : Callback<MovieDetailDTO> {
        override fun onResponse(call: Call<MovieDetailDTO>, response: Response<MovieDetailDTO>) {

            val serverResponse: MovieDetailDTO? = response.body()
            movieDetailLiveData.postValue(
                if (response.isSuccessful && serverResponse != null) {
                    checkResponse(serverResponse)
                } else {
                    MovieDetailState.Error(Throwable(SERVER_ERROR))
                }
            )
        }

        override fun onFailure(call: Call<MovieDetailDTO>, t: Throwable) {
            movieDetailLiveData.postValue(MovieDetailState.Error(t))
        }
    }

    private fun checkResponse(serverResponse: MovieDetailDTO): MovieDetailState {
        return if (checkMovieDetailModel(serverResponse)) {
            MovieDetailState.Success(serverResponse)
        } else {
            MovieDetailState.Error(Throwable(CORRUPTED_DATA))
        }
    }

    private fun checkMovieDetailModel(movieDetail: MovieDetailDTO): Boolean {
        return movieDetail != null
        // todo (Here can be placed movieDetail model)
    }
}