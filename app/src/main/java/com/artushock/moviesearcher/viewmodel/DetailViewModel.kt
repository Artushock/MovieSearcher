package com.artushock.moviesearcher.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.artushock.moviesearcher.model.MovieDetailDTO
import com.artushock.moviesearcher.model.MovieDetailState
import com.artushock.moviesearcher.model.repositories.RemoteDataSource
import com.artushock.moviesearcher.model.repositories.RepositoryMovieDetail
import com.artushock.moviesearcher.model.repositories.RepositoryMovieDetailImpl
import com.google.gson.Gson
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response
import java.io.IOException

const val SERVER_ERROR = "SERVER_ERROR"
const val CORRUPTED_DATA = "CORRUPTED_DATA"

class DetailViewModel(
    private val movieDetailLiveData: MutableLiveData<MovieDetailState> = MutableLiveData(),
    private val remoteRepository: RepositoryMovieDetail = RepositoryMovieDetailImpl(RemoteDataSource())
) : ViewModel() {

    fun getLiveData() = movieDetailLiveData

    fun getDetailLiveDataFromServer(requestLink: String) {
        movieDetailLiveData.value = MovieDetailState.Loading
        remoteRepository.getMovieDataFromServer(requestLink, callback)
    }

    private val callback = object : Callback {

        override fun onFailure(call: Call, e: IOException) {
            movieDetailLiveData.postValue(MovieDetailState.Error(e))
        }

        override fun onResponse(call: Call, response: Response) {
            val serverResponse: String? = response.body()?.string()
            movieDetailLiveData.postValue(
                if (response.isSuccessful && serverResponse != null) {
                    checkResponse(serverResponse)
                } else {
                    MovieDetailState.Error(Throwable(SERVER_ERROR))
                }
            )
        }

    }

    private fun checkResponse(serverResponse: String): MovieDetailState {
        val movieDetail: MovieDetailDTO =
            Gson().fromJson(serverResponse, MovieDetailDTO::class.java)
        return if (checkMovieDetailModel(movieDetail)) {
            MovieDetailState.Success(movieDetail)
        } else {
            MovieDetailState.Error(Throwable(CORRUPTED_DATA))
        }
    }

    private fun checkMovieDetailModel(movieDetail: MovieDetailDTO): Boolean {
        return movieDetail != null
        // todo (Here can be placed movieDetail model)
    }
}