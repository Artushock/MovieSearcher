package com.artushock.moviesearcher.view.fragments

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.artushock.moviesearcher.R
import com.artushock.moviesearcher.databinding.SearchFragmentBinding
import com.artushock.moviesearcher.model.dto.MovieDetailDTO
import com.artushock.moviesearcher.model.MovieListState
import com.artushock.moviesearcher.model.MovieLoaderByID
import com.artushock.moviesearcher.view.fragments.MOVIE_ID
import com.artushock.moviesearcher.view.adapters.MovieSearchAdapter
import com.artushock.moviesearcher.view.showSnackBar
import com.artushock.moviesearcher.viewmodel.SearchViewModel

class SearchFragment : Fragment() {
    private var _binding: SearchFragmentBinding? = null
    private val binding get() = _binding!!
    private val viewModel: SearchViewModel by lazy {
        ViewModelProvider(this).get(SearchViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = SearchFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.topRatedMoviesToObserve.observe(viewLifecycleOwner, { render(it) })
        viewModel.getTopRatedMovies()

        initSearch(viewModel)
    }

    private fun initSearch(viewModel: SearchViewModel) {
        val editText: EditText = binding.searchEditText
        val searchButton: Button = binding.searchButton

        searchButton.setOnClickListener {
            //todo
        }
    }

    private fun render(it: MovieListState?) {
        when (it) {
            is MovieListState.Loading -> {
                binding.searchFragmentProgressBar.visibility = View.VISIBLE
            }
            is MovieListState.Error -> {
                this.view?.showSnackBar(
                    getString(R.string.error),
                    getString(R.string.reload),
                    { viewModel.getTopRatedMovies() })
            }
            is MovieListState.Success -> {
                binding.searchFragmentProgressBar.visibility = View.GONE

                val searchRecyclerView: RecyclerView = binding.searchResultRecyclerView
                val layoutManager = LinearLayoutManager(context)
                val adapter = MovieSearchAdapter()

                searchRecyclerView.setHasFixedSize(true)
                searchRecyclerView.layoutManager = layoutManager
                adapter.movieList = it.moviesDTO.results

                adapter.onSearchedItemClickListener =
                    object : MovieSearchAdapter.OnSearchedItemClickListener {
                        @RequiresApi(Build.VERSION_CODES.N)
                        override fun onSearchedItemClick(id: Int) {
                            showDetailFragment(id)
                        }

                    }

                searchRecyclerView.adapter = adapter
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun showDetailFragment(id: Int) {
        val movieDetailListener: MovieLoaderByID.MovieDetailListener =
            object : MovieLoaderByID.MovieDetailListener {
                override fun onMovieDetailListener(movieDetail: MovieDetailDTO) {
                    val bundle = Bundle()
                    bundle.putParcelable(MOVIE_ID, movieDetail)
                    val navController = findNavController()
                    navController.navigate(R.id.movieDetailFragment, bundle)
                }

                override fun onMovieDetailFailed(e: Throwable) {
                    Toast.makeText(context, "Error: $e", Toast.LENGTH_SHORT).show()
                }
            }
        val movieLoaderByID = MovieLoaderByID(id, movieDetailListener)
        movieLoaderByID.loadMovie()
    }
}
