package com.artushock.moviesearcher.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.artushock.moviesearcher.R
import com.artushock.moviesearcher.databinding.SearchFragmentBinding
import com.artushock.moviesearcher.model.MovieListState
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

        viewModel.getMoviesLiveData().observe(viewLifecycleOwner, { render(it) })
        viewModel.getPopularMovieList()

        initSearch(viewModel)
    }

    private fun initSearch(viewModel: SearchViewModel) {
        val editText: EditText = binding.searchEditText
        val searchButton: Button = binding.searchButton

        /*searchButton.setOnClickListener {
            viewModel.findMovie(editText.text.toString())
        }*/
    }

    private fun render(it: MovieListState?) {
        when (it) {
            is MovieListState.Loading -> {
                binding.searchFragmentProgressBar.visibility = View.VISIBLE
            }

            is MovieListState.Success -> {
                binding.searchFragmentProgressBar.visibility = View.GONE

                val searchRecyclerView: RecyclerView = binding.searchResultRecyclerView
                searchRecyclerView.setHasFixedSize(true)
                val layoutManager = LinearLayoutManager(context)
                searchRecyclerView.layoutManager = layoutManager
                val adapter = MovieSearchAdapter()

                adapter.movieList = it.moviesDTO.results

                searchRecyclerView.adapter = adapter
            }
            is MovieListState.Error -> {
                this.view?.showSnackBar(
                    getString(R.string.error),
                    getString(R.string.reload),
                    { viewModel.getPopularMovieList() })
            }
        }
    }
}