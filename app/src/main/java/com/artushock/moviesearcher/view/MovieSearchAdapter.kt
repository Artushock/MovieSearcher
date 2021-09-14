package com.artushock.moviesearcher.view

import android.icu.text.CaseMap
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.artushock.moviesearcher.databinding.SearchedMovieItemBinding
import com.artushock.moviesearcher.model.Movie
import com.artushock.moviesearcher.model.MoviesDTO

class MovieSearchAdapter : RecyclerView.Adapter<MovieSearchAdapter.MovieSearchHolder>() {

    var onSearchedItemClickListener: OnSearchedItemClickListener? = null

    var movieList: List<MoviesDTO.MoviePreview> = listOf()
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

        fun bind(movie: MoviesDTO.MoviePreview) {
            name.text = movie.title
            releaseYear.text = movie.release_date
            country.text = "USA"
            rating.text = movie.vote_average.toString()

            item.setOnClickListener {
                onSearchedItemClickListener?.onSearchedItemClick(movie.title)
            }
        }
    }

    interface OnSearchedItemClickListener {
        fun onSearchedItemClick(title: String)
    }
}
