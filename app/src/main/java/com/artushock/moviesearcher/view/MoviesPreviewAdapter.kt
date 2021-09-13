package com.artushock.moviesearcher.view

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.artushock.moviesearcher.databinding.MovieItemBinding
import com.artushock.moviesearcher.model.Movie
import com.artushock.moviesearcher.model.MoviesDTO


class MoviesPreviewAdapter :
    RecyclerView.Adapter<MoviesPreviewAdapter.MoviesPreviewViewHolder>() {

    var movieItemClick : OnMovieItemClickListener? = null

    var movieList: List<MoviesDTO.MoviePreview> = listOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesPreviewViewHolder =
        MoviesPreviewViewHolder(
            MovieItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )

    override fun onBindViewHolder(holder: MoviesPreviewViewHolder, position: Int) {
        holder.bind(movieList[position])
    }

    override fun getItemCount(): Int {
        return movieList.size
    }

    inner class MoviesPreviewViewHolder(binding: MovieItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private val name: TextView = binding.movieNameItem
        private val genre: TextView = binding.movieGenreItem
        private val rating: TextView = binding.movieRateItem
        private val item: LinearLayout = binding.movieItemElement

        fun bind(movie: MoviesDTO.MoviePreview) {
            name.text = movie.title
            rating.text = movie.vote_average.toString()
            /*item.setOnClickListener{
                movieItemClick?.onMovieItemClick(movie)
            }*/
        }
    }

    interface OnMovieItemClickListener {
        fun onMovieItemClick(movie: Movie)
    }
}