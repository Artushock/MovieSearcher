package com.artushock.moviesearcher.view.adapters

import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.artushock.moviesearcher.databinding.MovieItemBinding
import com.artushock.moviesearcher.model.dto.MoviesDTO
import com.squareup.picasso.Picasso
import java.util.*


class MoviesPreviewAdapter :
    RecyclerView.Adapter<MoviesPreviewAdapter.MoviesPreviewViewHolder>() {

    var movieItemClick: OnMovieItemClickListener? = null

    var movieList: List<MoviesDTO.MoviePreview> = listOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesPreviewViewHolder =
        MoviesPreviewViewHolder(
            MovieItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )

    @RequiresApi(Build.VERSION_CODES.N)
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
        private val poster: ImageView = binding.movieItemPoster

        @RequiresApi(Build.VERSION_CODES.N)
        fun bind(movie: MoviesDTO.MoviePreview) {
            name.text = movie.title
            rating.text = movie.vote_average.toString()
            item.setOnClickListener {
                movieItemClick?.onMovieItemClick(movie.id)
            }
            movie.genre_names.let {
                genre.text = movie.genre_names[0].replaceFirstChar {
                    if (it.isLowerCase()) it.titlecase(
                        Locale.getDefault()
                    ) else it.toString()
                }
            }

            Picasso
                .get()
                .load("https://image.tmdb.org/t/p/w500/${movie.poster_path}")
                .fit()
                .into(poster)
        }
    }

    interface OnMovieItemClickListener {
        fun onMovieItemClick(movieID: Int)
    }
}