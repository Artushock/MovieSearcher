package com.artushock.moviesearcher.model

import android.os.Build
import android.os.Handler
import android.os.Looper
import androidx.annotation.RequiresApi
import com.artushock.moviesearcher.model.dto.MoviesDTO
import com.artushock.moviesearcher.model.repositories.RemoteDataSource
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@RequiresApi(Build.VERSION_CODES.N)
class MovieListsLoader(
    private val listener: MoviesListener,
    private val movieCategory: MovieCategory
) {

    @RequiresApi(Build.VERSION_CODES.N)
    fun loadMovies() {
        goToTheInternet(movieCategory)
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun goToTheInternet(movieCategory: MovieCategory) {

        val callback = object : Callback<MoviesDTO>{
            override fun onResponse(call: Call<MoviesDTO>, response: Response<MoviesDTO>) {
                val handler = Handler(Looper.getMainLooper())
                val moviesDTO: MoviesDTO? = response.body()

                handler.post {
                    moviesDTO?.let { listener.moviesLoaded(it, movieCategory) }
                }
            }

            override fun onFailure(call: Call<MoviesDTO>, t: Throwable) {
                listener.moviesLoadingFailed(t)
            }

        }
        when (movieCategory){
            MovieCategory.NOW_PLAYING -> {
                RemoteDataSource().getNowPlayingMoviesDataFromServer(1, callback)
            }
            MovieCategory.TOP_RATED -> {
                RemoteDataSource().getTopRatedMoviesDataFromServer(1, callback)
            }
            MovieCategory.POPULAR -> {
                RemoteDataSource().getPopularMoviesDataFromServer(1, callback)
            }
        }
    }

    interface MoviesListener {
        fun moviesLoaded(moviesDTO: MoviesDTO, movieCategory: MovieCategory)
        fun moviesLoadingFailed(e: Throwable)
    }
}