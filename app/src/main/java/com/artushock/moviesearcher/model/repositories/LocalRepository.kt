package com.artushock.moviesearcher.model.repositories

import com.artushock.moviesearcher.model.Movie
import com.artushock.moviesearcher.model.dto.MoviesDTO

interface LocalRepository {

    suspend fun getAllSeenMovies(): List<Movie>

    suspend fun saveEntity(movie: Movie)

    suspend fun deleteEntityByMovieId(id: Int)

    suspend fun clearDB()

    suspend fun checkMovieById(id: Int): Boolean
}