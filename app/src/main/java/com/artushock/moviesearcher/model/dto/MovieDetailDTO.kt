package com.artushock.moviesearcher.model.dto

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue

@Parcelize
class MovieDetailDTO(
    val budget: Int,
    val genres: @RawValue List<Genre>,
    val homepage: String,
    val id: Int,
    val original_language: String,
    val original_title: String,
    val overview: String,
    val production_companies: @RawValue List<ProductionCompany>,
    val release_date: String,
    val revenue: Int,
    val runtime: Int,
    val title: String,
    val vote_average: Double
) : Parcelable {
    inner class Genre(
        val id: Int,
        val name: String
    )

    inner class ProductionCompany(
        val id: Int,
        val name: String,
        val origin_country: String
    )
}