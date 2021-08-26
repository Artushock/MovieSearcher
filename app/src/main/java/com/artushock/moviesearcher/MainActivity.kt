package com.artushock.moviesearcher

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initData()
        initView()
    }

    private fun initView() {
        initButtons()
        initRecyclerView()
    }

    private fun initButtons() {
        
    }

    private fun initRecyclerView() {
        val movieRecyclerView: RecyclerView = findViewById(R.id.movie_list_rv)
        movieRecyclerView.setHasFixedSize(true)

        val layoutManager: LinearLayoutManager = LinearLayoutManager(this)
        movieRecyclerView.layoutManager = layoutManager

        val movieAdapter: MoviesAdapter = MoviesAdapter(MovieList)

        movieRecyclerView.adapter = movieAdapter
    }

    private fun initData() {
        MovieList.movies.add(Movie("Побег из Шоушенка", MovieGenre.DRAMA, 10))
        MovieList.movies.add(Movie("Крёстный отец", MovieGenre.ACTION, 10))
        MovieList.movies.add(Movie("Начало", MovieGenre.ACTION, 9))
        MovieList.movies.add(Movie("Москва слехам не верит", MovieGenre.ACTION, 9))
        MovieList.movies.add(Movie("Побег из Шоушенка", MovieGenre.DRAMA, 10))
        MovieList.movies.add(Movie("Крёстный отец", MovieGenre.ACTION, 10))
        MovieList.movies.add(Movie("Начало", MovieGenre.ACTION, 9))
        MovieList.movies.add(Movie("Москва слехам не верит", MovieGenre.ACTION, 9))
        MovieList.movies.add(Movie("Побег из Шоушенка", MovieGenre.DRAMA, 10))
        MovieList.movies.add(Movie("Крёстный отец", MovieGenre.ACTION, 10))
        MovieList.movies.add(Movie("Начало", MovieGenre.ACTION, 9))
        MovieList.movies.add(Movie("Москва слехам не верит", MovieGenre.ACTION, 9))
        MovieList.movies.add(Movie("Побег из Шоушенка", MovieGenre.DRAMA, 10))
        MovieList.movies.add(Movie("Крёстный отец", MovieGenre.ACTION, 10))
        MovieList.movies.add(Movie("Начало", MovieGenre.ACTION, 9))
        MovieList.movies.add(Movie("Москва слехам не верит", MovieGenre.ACTION, 9))
    }
}