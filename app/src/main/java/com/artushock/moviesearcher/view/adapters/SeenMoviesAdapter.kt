package com.artushock.moviesearcher.view.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.artushock.moviesearcher.databinding.SearchedMovieItemBinding
import com.artushock.moviesearcher.model.Movie

class SeenMoviesAdapter : RecyclerView.Adapter<SeenMoviesAdapter.MovieSearchHolder>() {

    var onSearchedItemClickListener: OnSearchedItemClickListener? = null

    var seenMoviesList: List<Movie> = listOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieSearchHolder =
        MovieSearchHolder(
            SearchedMovieItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )

    override fun onBindViewHolder(holder: MovieSearchHolder, position: Int) {
        holder.bind(seenMoviesList[position])
    }

    override fun getItemCount(): Int = seenMoviesList.size

    inner class MovieSearchHolder(binding: SearchedMovieItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private val name: TextView = binding.movieNameSearchItemRv
        private val releaseYear: TextView = binding.releaseDateSearchItemRv
        private val country: TextView = binding.countrySearchItemRv
        private val rating: TextView = binding.ratingSearchItemRv
        private val item: LinearLayout = binding.searchItem

        fun bind(movie: Movie) {
            name.text = movie.title
            releaseYear.text = movie.release_date.substring(0, 4)
            country.text = movie.language
            rating.text = movie.vote_average.toString()
            item.setOnClickListener {
                onSearchedItemClickListener?.onSearchedItemClick(movie.movieId)
            }
        }
    }

    interface OnSearchedItemClickListener {
        fun onSearchedItemClick(id: Int)
    }
}
