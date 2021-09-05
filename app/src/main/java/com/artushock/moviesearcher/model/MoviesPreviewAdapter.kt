package com.artushock.moviesearcher.model

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.artushock.moviesearcher.R
import java.util.*

class MoviesPreviewAdapter(private val data: ArrayList<Movie>) :
    RecyclerView.Adapter<MoviesPreviewAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val name: TextView = view.findViewById(R.id.movie_name_item)
        val genre: TextView = view.findViewById(R.id.movie_genre_item)
        val rating: TextView = view.findViewById(R.id.movie_rate_item)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.movie_item, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.name.text = data[position].name
        holder.genre.text = data[position].genre.genreName
        holder.rating.text = data[position].rating.toString()
    }

    override fun getItemCount(): Int {
        return data.size
    }
}