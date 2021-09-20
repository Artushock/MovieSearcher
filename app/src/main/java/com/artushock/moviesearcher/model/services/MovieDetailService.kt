package com.artushock.moviesearcher.model.services

import android.app.IntentService
import android.content.Intent
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.artushock.moviesearcher.BuildConfig
import com.artushock.moviesearcher.model.MovieDetailDTO
import com.artushock.moviesearcher.view.*
import com.google.gson.Gson
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.URL
import java.util.stream.Collectors
import javax.net.ssl.HttpsURLConnection

private const val REQUEST_GET = "GET"
private const val REQUEST_TIMEOUT = 10000
const val MOVIE_ID_EXTRA = "MOVIE_ID_EXTRA"

class MovieDetailService(val name: String = "MovieDetailService") : IntentService(name) {

    private val broadcastIntent = Intent(DETAILS_INTENT_FILTER)

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onHandleIntent(intent: Intent?) {
        if (intent == null) {
            onEmptyIntent()
        } else {
            val id = intent.getIntExtra(MOVIE_ID_EXTRA, -1)
            if (id != -1) {
                loadMovieDetail(id)
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun loadMovieDetail(id: Int) {
        try {
            val uri =
                URL("https://api.themoviedb.org/3/movie/$id?api_key=${BuildConfig.THEMOVIEDB_API_KEY}&language=ru-RU")

            var urlConnection: HttpsURLConnection? = null
            try {
                urlConnection = uri.openConnection() as HttpsURLConnection
                urlConnection.apply {
                    requestMethod = REQUEST_GET
                    readTimeout = REQUEST_TIMEOUT
                }

                val reader = BufferedReader(InputStreamReader(urlConnection.inputStream))
                val result = reader.lines().collect(Collectors.joining("\n"))

                val detail: MovieDetailDTO = Gson().fromJson(result, MovieDetailDTO::class.java)
                onResponse(detail)
            } catch (e: Exception) {
                onErrorRequest(e.message ?: "Empty error")
            } finally {
                urlConnection?.disconnect()
            }
        } catch (e: Exception) {
            onMalformedURL()
        }
    }

    private fun onResponse(movieDetail: MovieDetailDTO) {
        if (movieDetail == null) {
            onEmptyResponse()
        } else {
            onSuccessResponse(movieDetail)
        }
    }

    private fun onSuccessResponse(movieDetail: MovieDetailDTO) {
        putLoadResult(DETAILS_RESPONSE_SUCCESS_EXTRA)
        broadcastIntent.putExtra("DETAIL_MOVIE", movieDetail)
        sendBroadcast()
    }

    private fun onEmptyResponse() {
        putLoadResult(DETAILS_RESPONSE_EMPTY_EXTRA)
        sendBroadcast()
    }

    private fun onErrorRequest(error: String) {
        putLoadResult(DETAILS_REQUEST_ERROR_EXTRA)
        broadcastIntent.putExtra(DETAILS_REQUEST_ERROR_MESSAGE_EXTRA, error)
        sendBroadcast()
    }

    private fun onEmptyIntent() {
        putLoadResult(DETAILS_INTENT_EMPTY_EXTRA)
        sendBroadcast()
    }

    private fun onMalformedURL() {
        putLoadResult(DETAILS_URL_MALFORMED_EXTRA)
        sendBroadcast()
    }

    private fun putLoadResult(result: String) {
        broadcastIntent.putExtra(DETAILS_LOAD_RESULT_EXTRA, result)
    }

    private fun sendBroadcast() {
        LocalBroadcastManager.getInstance(this).sendBroadcast(broadcastIntent)
    }
}
