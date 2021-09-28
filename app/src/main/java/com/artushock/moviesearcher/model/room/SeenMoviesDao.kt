package com.artushock.moviesearcher.model.room

import androidx.room.*
import com.artushock.moviesearcher.model.Movie

@Dao
interface SeenMoviesDao {

    @Query("SELECT * FROM SeenMoviesEntity")
    suspend fun all(): List<SeenMoviesEntity>

    @Query("SELECT * FROM SeenMoviesEntity WHERE movieId = :movieId")
    suspend fun getMoviesById(movieId: Int): List<SeenMoviesEntity>

    @Query("DELETE FROM SeenMoviesEntity WHERE movieId = :movieId")
    suspend fun deleteMoviesByMovieId(movieId: Int)

    @Query("DELETE FROM SeenMoviesEntity")
    suspend fun clearSeenMoviesList()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entity: SeenMoviesEntity)

    @Update
    suspend fun update(entity: SeenMoviesEntity)

    @Delete
    suspend fun delete(entity: SeenMoviesEntity)
}