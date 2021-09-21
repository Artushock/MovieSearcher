package com.artushock.moviesearcher.model.repositories

import android.os.Build
import androidx.annotation.RequiresApi
import com.artushock.moviesearcher.model.MovieCategory
import com.artushock.moviesearcher.model.MovieListsLoader

class RepositoryMoviesImpl : RepositoryMovies {

    @RequiresApi(Build.VERSION_CODES.N)
    override fun getNewMovies(listener: MovieListsLoader.MoviesListener) {
        val loader = MovieListsLoader(listener, MovieCategory.NOW_PLAYING)
        loader.loadMovies()
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun getPopularMovies(listener: MovieListsLoader.MoviesListener) {
        val loader = MovieListsLoader(listener, MovieCategory.POPULAR)
        loader.loadMovies()
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun getTopRatedMovies(listener: MovieListsLoader.MoviesListener) {
        val loader = MovieListsLoader(listener, MovieCategory.TOP_RATED)
        loader.loadMovies()
    }


}