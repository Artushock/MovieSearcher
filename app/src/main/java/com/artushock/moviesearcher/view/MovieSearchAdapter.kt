package com.artushock.moviesearcher.view

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.artushock.moviesearcher.databinding.SearchedMovieItemBinding
import com.artushock.moviesearcher.model.Movie

class MovieSearchAdapter : RecyclerView.Adapter<MovieSearchAdapter.MovieSearchHolder>() {

    var onSearchedItemClickListener: OnSearchedItemClickListener? = null

    var movieList: List<Movie> = listOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieSearchHolder =
        MovieSearchHolder(
            SearchedMovieItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )

    override fun onBindViewHolder(holder: MovieSearchHolder, position: Int) {
        holder.bind(movieList[position])
    }

    override fun getItemCount(): Int = movieList.size

    inner class MovieSearchHolder(binding: SearchedMovieItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private val name: TextView = binding.movieNameSearchItemRv
        private val releaseYear: TextView = binding.releaseDateSearchItemRv
        private val country: TextView = binding.countrySearchItemRv
        private val rating: TextView = binding.ratingSearchItemRv
        private val item: LinearLayout = binding.searchItem

        fun bind(movie: Movie) {
            name.text = movie.name
            releaseYear.text = movie.releaseDate.formatYear()
            country.text = movie.country
            rating.text = movie.rating.toString()

            item.setOnClickListener {
                onSearchedItemClickListener?.onSearchedItemClick(movie)
            }
        }
    }

    interface OnSearchedItemClickListener {
        fun onSearchedItemClick(movie: Movie)
    }
}
