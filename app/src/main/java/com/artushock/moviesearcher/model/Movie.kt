package com.artushock.moviesearcher.model

data class Movie(
    val name: String = "",
    val genre: MovieGenre = MovieGenre.UNKNOWN,
    val rating: Float = 0f,
    val category: MovieCategory = MovieCategory.UNKNOWN
)
