package com.artushock.moviesearcher.view

import android.os.Bundle
import android.view.*
import androidx.core.view.MenuCompat
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
        viewModel.getLiveData().observe(viewLifecycleOwner, { render(it) })
        viewModel.getMovieList()
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
            }
            is MovieListState.Success -> {
                showMainViewers(data.moviesDTO)
            }
        }
    }

    private fun showMainViewers(movies: MoviesDTO) {
        binding.mainFragmentProgressBar.visibility = View.GONE

        val popularRV: RecyclerView = binding.popularMoviesRecyclerView
        initRecyclerView(popularRV, movies, MovieCategory.POPULAR)

        val newRV: RecyclerView = binding.newMoviesRecyclerView
        initRecyclerView(newRV, movies, MovieCategory.NEW)
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
            override fun onMovieItemClick(movie: Movie) {
                val manager = activity?.supportFragmentManager
                if (manager != null) {
                    val bundle = Bundle()
                    bundle.putParcelable(MOVIE_FOR_DETAIL, movie)
                    val navController = findNavController()
                    navController.navigate(R.id.movieDetailFragment, bundle)
                }
            }
        }
        rw.adapter = adapter
    }

    private fun getDividerItemDecoration(): DividerItemDecoration {
        val itemDecoration = DividerItemDecoration(context, LinearLayoutManager.HORIZONTAL)
        itemDecoration.setDrawable(resources.getDrawable(R.drawable.separator, null))
        return itemDecoration
    }
}
