package com.artushock.moviesearcher.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.artushock.moviesearcher.R
import com.artushock.moviesearcher.databinding.FragmentMovieDetailBinding
import com.artushock.moviesearcher.model.Movie
import com.artushock.moviesearcher.model.MovieDetailState
import com.artushock.moviesearcher.model.dto.MovieDetailDTO
import com.artushock.moviesearcher.viewmodel.DetailViewModel
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_movie_detail.*
import java.util.*

const val MOVIE_ID = "MOVIE_FOR_DETAIL"

class MovieDetailFragment : Fragment() {
    private var _binding: FragmentMovieDetailBinding? = null
    private val binding get() = _binding!!

    private val viewModel: DetailViewModel by lazy {
        ViewModelProvider(this).get(DetailViewModel::class.java)
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
        val movieId = arguments?.getInt(MOVIE_ID) ?: -1

        viewModel.movieDetailLiveData.observe(viewLifecycleOwner, { renderData(it) })
        if (movieId != -1) {
            viewModel.getDetailLiveDataFromServer(movieId)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun renderData(state: MovieDetailState) {
        when (state) {
            is MovieDetailState.Loading -> {
                binding.detailFragmentProgressBar.visibility = View.VISIBLE
            }
            is MovieDetailState.Error -> {
                binding.detailFragmentProgressBar.visibility = View.GONE
                showToast("${state.error}")
            }
            is MovieDetailState.Success -> {
                binding.detailFragmentProgressBar.visibility = View.GONE
                setMovieData(state.movie)
            }
        }
    }

    private fun setMovieData(movie: MovieDetailDTO) {
        with(binding) {
            add_to_seen_btn.isChecked = viewModel.isTheMovieExistInDb(movie.id)
            movie.let { movie ->
                add_to_seen_btn.setOnCheckedChangeListener { _, isChecked ->
                    val mv = with(movie) {
                        Movie(
                            id,
                            title,
                            original_language,
                            runtime,
                            release_date,
                            vote_average
                        )
                    }
                    if (isChecked) {
                        viewModel.saveSeenMovieToDataBase(mv)

                    } else {
                        viewModel.deleteFromSeenMovieToDataBase(mv)
                    }
                }
                add_to_seen_btn.setOnClickListener {

                }
                movieNameDetailTv.text = movie.title
                movieFullDetailTv.text = movie.original_title
                genreDetailTv.text = getGenres(movie)
                runningTimeDetailTv.text = getString(R.string.runtime, movie.runtime)
                ratingDetailTv.text = movie.vote_average.toString()
                budgetDetailTv.text = getString(R.string.budget_format, movie.budget)
                boxOfficeDetailTv.text = getString(R.string.box_office_format, movie.revenue)
                releaseDateDetailTv.text = movie.release_date
                descriptionDetailTv.text = movie.overview
                Picasso
                    .get()
                    .load("https://image.tmdb.org/t/p/w500/${movie.poster_path}")
                    .into(posterPathImage)
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

    private fun showToast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }
}