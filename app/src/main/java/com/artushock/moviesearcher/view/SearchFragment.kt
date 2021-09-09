package com.artushock.moviesearcher.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
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

        viewModel.getLiveData().observe(viewLifecycleOwner, { render(it) })
        viewModel.getMovieList()
    }

    private fun render(it: MovieListState?) {
        when (it) {
            is MovieListState.Loading -> {
                //
            }
            is MovieListState.SuccessRemote -> {
                val searchRecyclerView: RecyclerView = binding.searchResultRecyclerView
                searchRecyclerView.setHasFixedSize(true)
                val layoutManager = LinearLayoutManager(context)
                searchRecyclerView.layoutManager = layoutManager
                val adapter = MovieSearchAdapter()
                adapter.movieList = it.movieList
                searchRecyclerView.adapter = adapter
            }
            else -> {
                //
            }
        }
    }

}