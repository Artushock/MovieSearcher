package com.artushock.moviesearcher.view

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.artushock.moviesearcher.R
import com.artushock.moviesearcher.databinding.MainFragmentBinding
import com.artushock.moviesearcher.model.*
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
        viewModel.getMoviesLiveData().observe(viewLifecycleOwner, { render(it) })
        viewModel.getData()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun render(data: MovieListState) {
        when (data) {
            is MovieListState.Loading -> {
                binding.mainFragmentProgressBar.visibility = View.VISIBLE
            }
            is MovieListState.Error -> {
                binding.mainFragmentProgressBar.visibility = View.GONE
                view?.showSnackBar("Error ${data.e}", "Reload", {
                    viewModel.getData()
                })
            }
            is MovieListState.Success -> {

                when (data.movieCategory) {
                    MovieCategory.NEW -> {
                        val newMoviesRecyclerView: RecyclerView = binding.newMoviesRecyclerView
                        displayMovieList(data.moviesDTO, newMoviesRecyclerView)
                    }
                    MovieCategory.POPULAR -> {
                        val popularMoviesRecyclerView: RecyclerView =
                            binding.popularMoviesRecyclerView
                        displayMovieList(data.moviesDTO, popularMoviesRecyclerView)
                    }
                }
            }
        }
    }

    private fun displayMovieList(movies: MoviesDTO, recyclerView: RecyclerView) {
        binding.mainFragmentProgressBar.visibility = View.GONE
        initRecyclerView(recyclerView, movies)
    }

    private fun initRecyclerView(
        rw: RecyclerView,
        movies: MoviesDTO,
    ) {
        rw.setHasFixedSize(true)

        val linearLayoutManager = LinearLayoutManager(context)
        linearLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
        rw.layoutManager = linearLayoutManager
        rw.addItemDecoration(getDividerItemDecoration())

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
        val movieDetailListener: MovieLoaderByID.MovieDetailListener =
            object : MovieLoaderByID.MovieDetailListener {
                override fun onMovieDetailListener(movieDetail: MovieDetailDTO) {
                    val bundle = Bundle()
                    bundle.putParcelable(MOVIE_FOR_DETAIL, movieDetail)
                    val navController = findNavController()
                    navController.navigate(R.id.movieDetailFragment, bundle)
                }

                override fun onMovieDetailFailed(e: Throwable) {
                    Toast.makeText(context, "Error: $e", Toast.LENGTH_SHORT).show()
                }
            }
        val movieLoaderByID = MovieLoaderByID(movieID, movieDetailListener)
        movieLoaderByID.loadMovie()
    }

    private fun getDividerItemDecoration(): DividerItemDecoration {
        val itemDecoration = DividerItemDecoration(context, LinearLayoutManager.HORIZONTAL)
        itemDecoration.setDrawable(resources.getDrawable(R.drawable.separator, null))
        return itemDecoration
    }
}
