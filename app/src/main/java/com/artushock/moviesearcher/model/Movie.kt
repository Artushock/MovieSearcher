package com.artushock.moviesearcher.model

data class Movie(
        val name: String = "",
        val genre: MovieGenre = MovieGenre.DRAMA,
        val rating: Int = 10
)
