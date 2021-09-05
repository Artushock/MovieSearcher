package com.artushock.moviesearcher.view

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.core.view.MenuCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.artushock.moviesearcher.R
import com.artushock.moviesearcher.databinding.MainFragmentBinding
import com.artushock.moviesearcher.model.Movie
import com.artushock.moviesearcher.model.MovieCategory
import com.artushock.moviesearcher.model.MovieListState
import com.artushock.moviesearcher.viewmodel.MainViewModel
import com.google.android.material.snackbar.Snackbar

class MainFragment : Fragment() {
    private var _binding: MainFragmentBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.main_options_menu, menu)
        MenuCompat.setGroupDividerEnabled(menu, true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = MainFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        viewModel.getLiveData().observe(viewLifecycleOwner, { render(it, view) })
        viewModel.getMovieList()
    }


    private fun getDividerItemDecoration(): DividerItemDecoration {
        val itemDecoration = DividerItemDecoration(context, LinearLayoutManager.HORIZONTAL)
        itemDecoration.setDrawable(resources.getDrawable(R.drawable.separator, null))
        return itemDecoration
    }

    private fun render(data: MovieListState, view: View) {
        when (data) {
            is MovieListState.Loading -> {
                binding.mainFragmentProgressBar.visibility = View.VISIBLE
            }
            is MovieListState.Error -> {
                binding.mainFragmentProgressBar.visibility = View.GONE
                Snackbar.make(view, "Error", Snackbar.LENGTH_LONG)
                    .setAction("Reload") { viewModel.getMovieList() }
                    .show()
            }
            is MovieListState.SuccessLocal -> {
                binding.mainFragmentProgressBar.visibility = View.GONE
                val movies = data.movieList
                showMainViewers(movies)
                Toast.makeText(context, "Local data uploaded", Toast.LENGTH_SHORT).show()
            }

            is MovieListState.SuccessRemote -> {
                binding.mainFragmentProgressBar.visibility = View.GONE
                val movies = data.movieList
                showMainViewers(movies)
                Toast.makeText(context, "Remote data uploaded", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun showMainViewers(movies: ArrayList<Movie>) {
        val popularRV: RecyclerView = binding.popularMoviesRecyclerView
        initRecyclerView(popularRV, movies, MovieCategory.POPULAR)

        val newRV: RecyclerView = binding.newMoviesRecyclerView
        initRecyclerView(newRV, movies, MovieCategory.NEW)
    }

    private fun initRecyclerView(
        rw: RecyclerView,
        movies: ArrayList<Movie>,
        moviesCategory: MovieCategory
    ) {

        val actualMovies = getMoviesByCategory(movies, moviesCategory)
        rw.setHasFixedSize(true)

        val linearLayoutManager = LinearLayoutManager(context)
        linearLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
        rw.layoutManager = linearLayoutManager
        rw.addItemDecoration(getDividerItemDecoration())

        rw.adapter = MoviesPreviewAdapter(actualMovies)
    }


    private fun getMoviesByCategory(
        movies: ArrayList<Movie>,
        category: MovieCategory
    ): ArrayList<Movie> {
        val result = ArrayList<Movie>()
        for (movie in movies) {
            if (movie.category == category) {
                result.add(movie)
            }
        }
        return result
    }

}