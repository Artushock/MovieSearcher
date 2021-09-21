package com.artushock.moviesearcher.model

class GenresDTO(
    val genres: List<Genre>
) {

    class Genre(
        val id: Int,
        val name: String
    )
}