package com.artushock.moviesearcher.model.dto

class MoviesDTO(
    val page: Int,
    var results: List<MoviePreview>,
    val total_pages: Int,
    val total_results: Int
) {

    class MoviePreview(
        val adult: Boolean,
        val id: Int,
        val release_date: String,
        val original_language: String,
        val genre_ids: List<Int>,
        var genre_names: List<String>,
        val title: String,
        val vote_average: Float,
        val poster_path: String
    )
}