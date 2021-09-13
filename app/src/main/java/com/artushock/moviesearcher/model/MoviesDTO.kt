package com.artushock.moviesearcher.model

class MoviesDTO(
    val page: Int,
    val results: List<MoviePreview>,
    val total_pages: Int,
    val total_results: Int
) {

    class MoviePreview(
        val id: Int,
        val overview: String,
        val release_date: String,
        val title: String,
        val vote_average: Float,
    ) {
        //
    }
}