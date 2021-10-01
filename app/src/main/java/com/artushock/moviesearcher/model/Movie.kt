package com.artushock.moviesearcher.model

import kotlinx.android.parcel.RawValue

data class Movie(
    val movieId: Int,
    val title: String,
    val language: String,
    val runtime: Int,
    val release_date: String,
    val vote_average: Double,
    val comment: String
)