package com.artushock.moviesearcher.view

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.artushock.moviesearcher.R
import com.artushock.moviesearcher.databinding.FragmentMovieDetailBinding
import com.artushock.moviesearcher.model.MovieDetailDTO
import com.artushock.moviesearcher.model.services.MOVIE_ID_EXTRA
import com.artushock.moviesearcher.model.services.MovieDetailService
import java.util.*

const val MOVIE_ID = "MOVIE_FOR_DETAIL"
const val DETAILS_INTENT_FILTER = "DETAILS INTENT FILTER"
const val DETAILS_LOAD_RESULT_EXTRA = "LOAD RESULT"
const val DETAILS_RESPONSE_SUCCESS_EXTRA = "RESPONSE SUCCESS"
const val DETAILS_RESPONSE_EMPTY_EXTRA = "DETAILS_RESPONSE_EMPTY_EXTRA"
const val DETAILS_INTENT_EMPTY_EXTRA = "DETAILS_INTENT_EMPTY_EXTRA"
const val DETAILS_REQUEST_ERROR_EXTRA = "DETAILS_REQUEST_ERROR_EXTRA"
const val DETAILS_REQUEST_ERROR_MESSAGE_EXTRA = "DETAILS_REQUEST_ERROR_MESSAGE_EXTRA"
const val DETAILS_URL_MALFORMED_EXTRA = "DETAILS_URL_MALFORMED_EXTRA"


class MovieDetailFragment : Fragment() {
    private var _binding: FragmentMovieDetailBinding? = null
    private val binding get() = _binding!!

    val movieID: Int = -1
    val loadResultReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            when (intent?.getStringExtra(DETAILS_LOAD_RESULT_EXTRA)) {
                DETAILS_RESPONSE_SUCCESS_EXTRA -> {
                    renderData(
                        intent.getParcelableExtra("DETAIL_MOVIE")
                    )
                }
                DETAILS_URL_MALFORMED_EXTRA -> {
                    showToast("DETAILS_URL_MALFORMED_EXTRA")
                }
                DETAILS_INTENT_EMPTY_EXTRA -> {
                    showToast("DETAILS_INTENT_EMPTY_EXTRA")
                }
                DETAILS_REQUEST_ERROR_EXTRA -> {
                    val error = intent.getStringExtra(DETAILS_REQUEST_ERROR_MESSAGE_EXTRA)
                    showToast("Error $error")
                }
                DETAILS_RESPONSE_EMPTY_EXTRA -> {
                    showToast("DETAILS_RESPONSE_EMPTY_EXTRA")
                }
            }
        }

    }

    private fun showToast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    private fun renderData(movie: MovieDetailDTO?) {
        with(binding) {
            movie?.let { movie ->
                movieNameDetailTv.text = movie.title
                movieFullDetailTv.text = movie.original_title
                genreDetailTv.text = getGenres(movie)
                runningTimeDetailTv.text = getString(R.string.runtime, movie.runtime)
                ratingDetailTv.text = movie.vote_average.toString()
                budgetDetailTv.text = getString(R.string.budget_format, movie.budget)
                boxOfficeDetailTv.text = getString(R.string.box_office_format, movie.revenue)
                releaseDateDetailTv.text = movie.release_date
                descriptionDetailTv.text = movie.overview
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        context?.let {
            LocalBroadcastManager.getInstance(it)
                .registerReceiver(
                    loadResultReceiver,
                    IntentFilter(DETAILS_INTENT_FILTER)
                )
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMovieDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getMovieById()
    }

    private fun getMovieById() {
        val id = arguments?.getInt(MOVIE_ID) ?: -1
        context?.startService(Intent(context, MovieDetailService::class.java).apply {
            putExtra(
                MOVIE_ID_EXTRA,
                id
            )
        })
    }

    private fun getGenres(movie: MovieDetailDTO): String {
        var genresStr = ""
        for (s in movie.genres) {
            genresStr += "${
                s.name.replaceFirstChar {
                    if (it.isLowerCase()) it.titlecase(
                        Locale.ROOT
                    ) else it.toString()
                }
            }\n"
        }
        genresStr = genresStr.trim()
        return genresStr
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null

        context?.let {
            LocalBroadcastManager.getInstance(it)
                .unregisterReceiver(
                    loadResultReceiver
                )
        }
    }
}