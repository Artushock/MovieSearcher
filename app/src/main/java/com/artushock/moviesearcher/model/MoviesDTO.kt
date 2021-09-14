package com.artushock.moviesearcher.model

class MoviesDTO(
    val page: Int,
    val results: List<MoviePreview>,
    val total_pages: Int,
    val total_results: Int
) {

    class MoviePreview(
        val id: Int,
        val release_date: String,
        val original_language: String,
        val title: String,
        val vote_average: Float,
    )
}