package com.artushock.moviesearcher.model.dto

class GenresDTO(
    val genres: List<Genre>
) {

    class Genre(
        val id: Int,
        val name: String
    )
}