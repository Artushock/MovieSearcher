package com.artushock.moviesearcher.model

import android.os.Build
import androidx.annotation.RequiresApi

class RepositoryApiImpl : RepositoryAPI {

    @RequiresApi(Build.VERSION_CODES.N)
    override fun getNewMovies(listener: MovieLoader.MoviesListener) {
        val loader = MovieLoader(listener, MovieCategory.NEW)
        loader.loadMovies()
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun getPopularMovies(listener: MovieLoader.MoviesListener) {
        val loader = MovieLoader(listener, MovieCategory.POPULAR)
        loader.loadMovies()
    }


}