package com.artushock.moviesearcher.model

import android.os.Build
import android.os.Handler
import android.os.Looper
import androidx.annotation.RequiresApi
import com.artushock.moviesearcher.BuildConfig
import com.google.gson.Gson
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.URL
import java.util.stream.Collectors
import javax.net.ssl.HttpsURLConnection

class MovieLoaderByID(
    private val id: Int,
    private val listener: MovieDetailListener
) {
    @RequiresApi(Build.VERSION_CODES.N)
    fun loadMovie() {
        val uri =
            URL("https://api.themoviedb.org/3/movie/$id?api_key=${BuildConfig.THEMOVIEDB_API_KEY}&language=ru-RU")
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
            val movieDetailDTO: MovieDetailDTO = Gson().fromJson(result, MovieDetailDTO::class.java)

            handler.post {
                listener.onMovieDetailListener(movieDetailDTO)
            }
        } catch (e: Exception) {
            listener.onMovieDetailFailed(e)
        } finally {
            urlConnection?.disconnect()
        }
    }

    interface MovieDetailListener {
        fun onMovieDetailListener(movieDetail: MovieDetailDTO)
        fun onMovieDetailFailed(e: Throwable)
    }
}
