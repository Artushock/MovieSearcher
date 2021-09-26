package com.artushock.moviesearcher.model.repositories

import com.artushock.moviesearcher.model.Movie
import com.artushock.moviesearcher.model.dto.MoviesDTO

interface LocalRepository {

    fun getAllSeenMovies(): List<Movie>

    fun saveEntity(movie: Movie)

    fun deleteEntityByMovieId(id: Int)

    fun clearDB()

    fun checkMovieById(id: Int): Boolean
}