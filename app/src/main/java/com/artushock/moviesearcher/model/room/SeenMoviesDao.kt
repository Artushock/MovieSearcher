package com.artushock.moviesearcher.model.room

import androidx.room.*
import com.artushock.moviesearcher.model.Movie

@Dao
interface SeenMoviesDao {

    @Query("SELECT * FROM SeenMoviesEntity")
    fun all(): List<SeenMoviesEntity>

    @Query("SELECT * FROM SeenMoviesEntity WHERE movieId = :movieId")
    fun getMoviesById(movieId: Int): List<SeenMoviesEntity>

    @Query("DELETE FROM SeenMoviesEntity WHERE movieId = :movieId")
    fun deleteMoviesByMovieId(movieId: Int)

    @Query("DELETE FROM SeenMoviesEntity")
    fun clearSeenMoviesList()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(entity: SeenMoviesEntity)

    @Update
    fun update(entity: SeenMoviesEntity)

    @Delete
    fun delete(entity: SeenMoviesEntity)
}