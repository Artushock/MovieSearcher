package com.artushock.moviesearcher

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MoviesAdapter(private val data: MovieList) : RecyclerView.Adapter<MoviesAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val movieName: TextView = view.findViewById(R.id.movie_name_tv)
        val movieGenre: TextView = view.findViewById(R.id.movie_genre_tv)
        val movieRating: TextView = view.findViewById(R.id.movie_rating_tv)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater
                .from(parent.context)
                .inflate(R.layout.movie_item_layout, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.movieName.text = data.movies[position].name
        holder.movieGenre.text = data.movies[position].genre.toString()
        holder.movieRating.text = data.movies[position].rating.toString()
    }

    override fun getItemCount() = data.movies.size
}