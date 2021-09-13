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
class MovieLoader(
    private val listener: MoviesListener,
    private val movieType: MovieType
) {
    private val tag = "[Art_MovieLoader]"


    @RequiresApi(Build.VERSION_CODES.N)
    fun loadMovies() {
        val uri = movieType.uri
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

            Log.d(tag, "CONGRATULATIONS!!!! ${moviesDTO.results[1].id}")

            handler.post {
                listener.moviesLoaded(moviesDTO)
            }
        } catch (e: Exception) {
            Log.e(tag, "Fail connection", e)
            listener.moviesFailed(e)
        } finally {
            urlConnection?.disconnect()
        }
    }

    interface MoviesListener {
        fun moviesLoaded(newMoviesDTO: MoviesDTO)
        fun moviesFailed(e: Throwable)
    }

    enum class MovieType(val uri: URL) {
        NEW(URL("https://api.themoviedb.org/3/movie/now_playing?api_key=051e149465ccfab1113dcf96b1096d37&language=ru-RU&page=1&region=ru")),
        POPULAR(URL("https://api.themoviedb.org/3/movie/popular?api_key=051e149465ccfab1113dcf96b1096d37&language=ru-RU&page=1"))
    }

}