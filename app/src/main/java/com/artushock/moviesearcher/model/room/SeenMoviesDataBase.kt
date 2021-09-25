package com.artushock.moviesearcher.model.room

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = arrayOf(SeenMoviesEntity::class), version = 1, exportSchema = false)
abstract class SeenMoviesDataBase : RoomDatabase() {

    abstract fun seenMoviesDao(): SeenMoviesDao
}