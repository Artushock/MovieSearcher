package com.artushock.moviesearcher.view.fragments

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.artushock.moviesearcher.R
import com.artushock.moviesearcher.databinding.MainFragmentBinding
import com.artushock.moviesearcher.model.MovieCategory
import com.artushock.moviesearcher.model.states.MovieListState
import com.artushock.moviesearcher.model.dto.MoviesDTO
import com.artushock.moviesearcher.view.adapters.MoviesPreviewAdapter
import com.artushock.moviesearcher.view.showSnackBar
import com.artushock.moviesearcher.viewmodel.MainViewModel

class MainFragment : Fragment() {
    private var _binding: MainFragmentBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MainViewModel by lazy {
        ViewModelProvider(this).get(MainViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = MainFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.popularMoviesToObserve.observe(viewLifecycleOwner, { render(it) })
        viewModel.nowPlayingMoviesToObserve.observe(viewLifecycleOwner, { render(it) })
        viewModel.topRatedMoviesToObserve.observe(viewLifecycleOwner, { render(it) })
        viewModel.getMovies()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun render(data: MovieListState) {
        when (data) {
            is MovieListState.Loading -> {
                binding.loadingLayout.fragmentProgressBar.visibility = View.VISIBLE
            }
            is MovieListState.Error -> {
                binding.loadingLayout.fragmentProgressBar.visibility = View.GONE
                view?.showSnackBar("Error ${data.e}", "Reload", {
                    viewModel.getMovies()
                })
            }
            is MovieListState.Success -> {
                when (data.movieCategory) {
                    MovieCategory.NOW_PLAYING -> {
                        displayMovieList(data.moviesDTO, binding.newMoviesRecyclerView)
                    }
                    MovieCategory.POPULAR -> {
                        displayMovieList(data.moviesDTO, binding.popularMoviesRecyclerView)
                    }
                    MovieCategory.TOP_RATED -> {
                        displayMovieList(data.moviesDTO, binding.topRatedMoviesRecyclerView)
                    }
                }
            }
        }
    }

    private fun displayMovieList(movies: MoviesDTO, recyclerView: RecyclerView) {
        binding.loadingLayout.fragmentProgressBar.visibility = View.GONE
        initRecyclerView(recyclerView, movies)
    }

    private fun initRecyclerView(
        rw: RecyclerView,
        movies: MoviesDTO
    ) {
        rw.setHasFixedSize(true)

        val linearLayoutManager = LinearLayoutManager(context)
        linearLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
        rw.layoutManager = linearLayoutManager

        val adapter = MoviesPreviewAdapter()
        adapter.movieList = movies.results

        adapter.movieItemClick = object : MoviesPreviewAdapter.OnMovieItemClickListener {

            @RequiresApi(Build.VERSION_CODES.N)
            override fun onMovieItemClick(movieID: Int) {
                showDetailFragment(movieID)
            }
        }
        rw.adapter = adapter
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun showDetailFragment(movieID: Int) {
        val bundle = Bundle()
        bundle.putInt(MOVIE_ID, movieID)
        val navController = findNavController()
        navController.navigate(R.id.movieDetailFragment, bundle)
    }
}
