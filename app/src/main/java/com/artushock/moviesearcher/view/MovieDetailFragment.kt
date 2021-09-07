package com.artushock.moviesearcher.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.artushock.moviesearcher.R
import com.artushock.moviesearcher.databinding.FragmentMovieDetailBinding
import com.artushock.moviesearcher.model.Movie

const val MOVIE_FOR_DETAIL = "MOVIE_FOR_DETAIL"


class MovieDetailFragment : Fragment() {
    private var _binding: FragmentMovieDetailBinding? = null
    private val binding get() = _binding!!

    private var movie: Movie? = null

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
        binding.movieNameDetailTv.text = movie?.name
        binding.movieFullDetailTv.text = movie?.extensionalName
        binding.genreDetailTv.text = movie?.mainGenre?.genreName
        binding.runningTimeDetailTv.text = movie?.runningTime.toString()
        binding.ratingDetailTv.text = movie?.rating.toString()
        binding.budgetDetailTv.text = getString(R.string.budget_format, movie?.budget)
        binding.boxOfficeDetailTv.text = getString(R.string.box_office_format, movie?.boxOffice)
        binding.releaseDateDetailTv.text = movie?.releaseDate.toString()
        binding.descriptionDetailTv.text = movie?.description
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


    companion object {
        fun newInstance(bundle: Bundle) =
            MovieDetailFragment().apply {
                arguments = bundle.apply {
                    putParcelable(MOVIE_FOR_DETAIL, movie)
                }
            }
    }
}