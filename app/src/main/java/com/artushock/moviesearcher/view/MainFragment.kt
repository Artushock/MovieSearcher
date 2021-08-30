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
import com.artushock.moviesearcher.model.MovieGenre
import com.artushock.moviesearcher.model.MoviesPreviewAdapter
import com.artushock.moviesearcher.viewmodel.MainViewModel
import java.util.*

class MainFragment : Fragment() {
    private var _binding: MainFragmentBinding? = null
    private val binding get() = _binding!!

    ///

    ///

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
            //TODO: Implements after defining menus
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
        // TODO: Use the ViewModel

        val newMoviesRecyclerView : RecyclerView = view.findViewById(R.id.new_movies_recycler_view)
        newMoviesRecyclerView.setHasFixedSize(true)


        val newMoviesLayoutManager = LinearLayoutManager(context)
        newMoviesLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
        newMoviesRecyclerView.layoutManager = newMoviesLayoutManager

        val itemDecoration = DividerItemDecoration (context, LinearLayoutManager.HORIZONTAL)
        itemDecoration.setDrawable(resources.getDrawable(R.drawable.separator, null))
        newMoviesRecyclerView.addItemDecoration(itemDecoration)
        newMoviesRecyclerView.adapter = MoviesPreviewAdapter(initData())
    }

    private fun initData(): ArrayList<Movie> {
        val data = ArrayList<Movie>()
        data.add(Movie("Побег из Шоушенка", MovieGenre.DRAMA, 10f))
        data.add(Movie("Крёстный отец", MovieGenre.ACTION, 10f))
        data.add(Movie("Начало", MovieGenre.ACTION, 9f))
        data.add(Movie("Москва слехам не верит", MovieGenre.ACTION, 9f))
        data.add(Movie("Форрест Гамп", MovieGenre.DRAMA, 10f))
        data.add(Movie("Матрица", MovieGenre.ACTION, 10f))
        data.add(Movie("Пролетая над гнездом кукушки", MovieGenre.ACTION, 9f))
        data.add(Movie("Город Бога", MovieGenre.ACTION, 9f))
        data.add(Movie("Молчание ягнят", MovieGenre.DRAMA, 10f))
        data.add(Movie("Леон", MovieGenre.ACTION, 10f))
        data.add(Movie("Американская история Икс", MovieGenre.ACTION, 9f))
        data.add(Movie("Касабланка", MovieGenre.ACTION, 9f))
        data.add(Movie("Однажды на Диком Западеа", MovieGenre.WESTERN, 8f))
        data.add(Movie("Чужой", MovieGenre.ACTION, 8f))
        data.add(Movie("Великий диктатор", MovieGenre.COMEDY, 9f))
        data.add(Movie("Москва слехам не верит", MovieGenre.ACTION, 9f))
        return data
    }

}