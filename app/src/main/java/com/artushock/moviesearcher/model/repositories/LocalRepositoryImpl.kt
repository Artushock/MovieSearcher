package com.artushock.moviesearcher.model.repositories

import com.artushock.moviesearcher.model.Movie
import com.artushock.moviesearcher.model.room.SeenMoviesDao
import com.artushock.moviesearcher.model.room.SeenMoviesEntity

class LocalRepositoryImpl(private val localDataSource: SeenMoviesDao) : LocalRepository {

    override suspend fun getAllSeenMovies(): List<Movie> {
        return convertSeenMovieEntityToMovie(localDataSource.all())
    }

    override suspend fun saveEntity(movie: Movie) {
        localDataSource.insert(convertMovieToSeenMovieEntity(movie))
    }

    override suspend fun deleteEntityByMovieId(id: Int) {
        localDataSource.deleteMoviesByMovieId(id)
    }


    override suspend fun clearDB() {
        localDataSource.clearSeenMoviesList()
    }

    override suspend fun checkMovieById(id: Int): Boolean {
        val moviesEntities: List<SeenMoviesEntity> = localDataSource.getMoviesById(id)
        return moviesEntities.isEmpty()
    }

    private fun convertSeenMovieEntityToMovie(seenMovieList: List<SeenMoviesEntity>): List<Movie> {
        return seenMovieList.map {
            Movie(it.movieId, it.name, it.lang, it.runtime, it.releaseDate, it.rating, it.comment)
        }
    }

    private fun convertMovieToSeenMovieEntity(movie: Movie): SeenMoviesEntity {
        with(movie) {
            return SeenMoviesEntity(
                0,
                movieId,
                title,
                language,
                runtime,
                release_date,
                vote_average,
                comment
            )
        }

    }
}
