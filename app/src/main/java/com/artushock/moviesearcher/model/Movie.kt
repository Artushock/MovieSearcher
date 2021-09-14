package com.artushock.moviesearcher.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
data class Movie(
    val name: String = "Unknown movie name",
    val extensionalName: String = "Unknown full name of movie",
    val mainGenre: MovieGenre = MovieGenre.UNKNOWN,
    val containedGenreSet: ArrayList<MovieGenre> = arrayListOf(),
    val rating: Float = 0f,
    //val category: MovieCategory = MovieCategory.UNKNOWN,
    val budget: Int = 0,
    val boxOffice: Int = 0,
    val revenue: Int = 0,
    val runningTime: Int = 0,
    val description: String = "Description isn't added",
    val director: String = "Unknown director",
    val mainActorsSet: ArrayList<String> = arrayListOf(),
    val fullActorsSet: ArrayList<String> = arrayListOf(),
    val releaseDate: Date = Date(),
    val country: String = "Country"
) : Parcelable
