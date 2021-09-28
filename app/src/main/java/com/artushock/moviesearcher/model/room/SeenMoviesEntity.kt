package com.artushock.moviesearcher.model.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class SeenMoviesEntity(

    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val movieId: Int,
    val name: String,
    val lang: String,
    val runtime: Int,
    val releaseDate: String,
    val rating: Double,
    val comment: String
)