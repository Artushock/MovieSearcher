package com.artushock.moviesearcher.model

import android.os.Build
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.annotation.RequiresApi
import com.google.gson.Gson
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.URL
import java.util.stream.Collectors
import javax.net.ssl.HttpsURLConnection

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
        var urlConnection: HttpsURLConnection? = null
        val handler = Handler(Looper.getMainLooper())
        try {
            urlConnection = uri.openConnection() as HttpsURLConnection
            urlConnection.requestMethod = "GET"

            val reader = BufferedReader(InputStreamReader(urlConnection.inputStream))
            val result = reader.lines().collect(Collectors.joining("\n"))
            val moviesDTO: MoviesDTO = Gson().fromJson(result, MoviesDTO::class.java)

            handler.post {
                listener.moviesLoaded(moviesDTO, movieCategory)
            }
        } catch (e: Exception) {
            listener.moviesFailed(e)
        } finally {
            urlConnection?.disconnect()
        }
    }

    interface MoviesListener {
        fun moviesLoaded(moviesDTO: MoviesDTO, movieCategory: MovieCategory)
        fun moviesFailed(e: Throwable)
    }
}