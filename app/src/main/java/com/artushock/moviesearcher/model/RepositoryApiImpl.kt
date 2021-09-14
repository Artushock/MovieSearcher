package com.artushock.moviesearcher.model

import android.os.Build
import androidx.annotation.RequiresApi

class RepositoryApiImpl : RepositoryAPI {

    @RequiresApi(Build.VERSION_CODES.N)
    override fun getNewMovies(listener: MovieListsLoader.MoviesListener) {
        val loader = MovieListsLoader(listener, MovieCategory.NEW)
        loader.loadMovies()
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun getPopularMovies(listener: MovieListsLoader.MoviesListener) {
        val loader = MovieListsLoader(listener, MovieCategory.POPULAR)
        loader.loadMovies()
    }


}