package com.artushock.moviesearcher.model.room

import androidx.room.*

@Dao
interface SeenMoviesDao {

    @Query("SELECT * FROM SeenMoviesEntity")
    fun all(): List<SeenMoviesEntity>
//
//    @Query("SELECT * FROM SeenMoviesEntity WHERE releaseDate LIKE :year")
//    fun getMoviesByYear(releaseDate: String): List<SeenMoviesEntity>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(entity: SeenMoviesEntity)

    @Update
    fun update(entity: SeenMoviesEntity)

    @Delete
    fun delete(entity: SeenMoviesEntity)
}