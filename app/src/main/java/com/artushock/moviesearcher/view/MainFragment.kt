package com.artushock.moviesearcher.view

import android.os.Bundle
import android.view.*
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
import com.artushock.moviesearcher.model.MoviesPreviewAdapter
import com.artushock.moviesearcher.viewmodel.MainViewModel
import com.google.android.material.snackbar.Snackbar

class MainFragment : Fragment() {
    private var _binding: MainFragmentBinding? = null
    private val binding get() = _binding!!

    companion object {
        fun newInstance() = MainFragment()
    }

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

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        return when (item.itemId) {
            else -> false
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
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
        viewModel.getMovieListFromLocalStorage()
    }

    private fun initNewMoviesRecyclerView(movies: ArrayList<Movie>) {
        val newMoviesRecyclerView: RecyclerView = binding.newMoviesRecyclerView
        newMoviesRecyclerView.setHasFixedSize(true)

        val newMoviesLayoutManager = LinearLayoutManager(context)
        newMoviesLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
        newMoviesRecyclerView.layoutManager = newMoviesLayoutManager

        newMoviesRecyclerView.addItemDecoration(getDividerItemDecoration())

        newMoviesRecyclerView.adapter = MoviesPreviewAdapter(movies)
    }

    private fun initPopularMoviesRecyclerView(movies: ArrayList<Movie>) {
        val popularMoviesRecyclerView: RecyclerView = binding.popularMoviesRecyclerView
        popularMoviesRecyclerView.setHasFixedSize(true)

        val popularMoviesLayoutManager = LinearLayoutManager(context)
        popularMoviesLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
        popularMoviesRecyclerView.layoutManager = popularMoviesLayoutManager

        popularMoviesRecyclerView.addItemDecoration(getDividerItemDecoration())


        popularMoviesRecyclerView.adapter = MoviesPreviewAdapter(movies)
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
                    .setAction("Reload") {viewModel.getMovieListFromLocalStorage()}
                    .show()
            }
            is MovieListState.SuccessLocal -> {
                binding.mainFragmentProgressBar.visibility = View.GONE
                val movies = data.movieList
                initNewMoviesRecyclerView(getMoviesByCategory(movies, MovieCategory.NEW))
                initPopularMoviesRecyclerView(getMoviesByCategory(movies, MovieCategory.POPULAR))
            }
        }
    }

    private fun getMoviesByCategory(movies: ArrayList<Movie>, category: MovieCategory): ArrayList<Movie> {
        val result = ArrayList<Movie>()
        for (movie in movies){
            if (movie.category == category){
                result.add(movie)
            }
        }
        return result
    }

}