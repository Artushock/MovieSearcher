package com.artushock.moviesearcher.model

import android.os.Build
import android.os.Handler
import android.os.Looper
import androidx.annotation.RequiresApi
import com.artushock.moviesearcher.model.repositories.RemoteDataSource
import com.google.gson.Gson
import okhttp3.*
import java.io.IOException
import java.net.URL

@RequiresApi(Build.VERSION_CODES.N)
class MovieListsLoader(
    private val listener: MoviesListener,
    private val movieCategory: MovieCategory
) {

    @RequiresApi(Build.VERSION_CODES.N)
    fun loadMovies() {
        val uri = movieCategory.uri
        Thread {
            goToTheInternet(uri)
        }.start()
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun goToTheInternet(uri: URL) {

        val callback = object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                listener.moviesLoadingFailed(e)
            }

            override fun onResponse(call: Call, response: Response) {
                val handler = Handler(Looper.getMainLooper())
                val result: String? = response.body()?.string()
                val moviesDTO: MoviesDTO = Gson().fromJson(result, MoviesDTO::class.java)

                handler.post {
                    listener.moviesLoaded(moviesDTO, movieCategory)
                }
            }
        }
        RemoteDataSource().getRemoteData(uri.toString(), callback)
    }

    interface MoviesListener {
        fun moviesLoaded(moviesDTO: MoviesDTO, movieCategory: MovieCategory)
        fun moviesLoadingFailed(e: Throwable)
    }
}