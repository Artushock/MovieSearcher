package com.artushock.moviesearcher.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.artushock.moviesearcher.R
import com.artushock.moviesearcher.databinding.FragmentMovieDetailBinding
import com.artushock.moviesearcher.model.MovieDetailDTO
import java.util.*

const val MOVIE_FOR_DETAIL = "MOVIE_FOR_DETAIL"

class MovieDetailFragment : Fragment() {
    private var _binding: FragmentMovieDetailBinding? = null
    private val binding get() = _binding!!

    private var movie: MovieDetailDTO? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            movie = it.getParcelable(MOVIE_FOR_DETAIL)
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
    }
}