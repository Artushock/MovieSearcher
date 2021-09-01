package com.artushock.moviesearcher

import java.util.*

fun getStringByGenre(genre: MovieGenre): String {
    return when (genre) {
        MovieGenre.ACTION -> "Action film"
        MovieGenre.WESTERN -> "Western"
        MovieGenre.GANGSTER -> "Gangster film"
        MovieGenre.DETECTIVE -> "Detective"
        MovieGenre.DRAMA -> "Drama"
        MovieGenre.HISTORICAL -> "Historical film"
        MovieGenre.COMEDY -> "Comedy"
        MovieGenre.MELODRAMA -> "Melodrama"
        MovieGenre.MUSIC -> "Music film"
        MovieGenre.NOIR -> "Noir"
        MovieGenre.UNKNOWN -> "Unknown"
    }
}

fun getGenreByString(string: String): MovieGenre {
    return when (string) {
        "Action film" -> MovieGenre.ACTION
        "Western" -> MovieGenre.WESTERN
        "Gangster film" -> MovieGenre.GANGSTER
        "Detective" -> MovieGenre.DETECTIVE
        "Drama" -> MovieGenre.DRAMA
        "Historical film" -> MovieGenre.HISTORICAL
        "Comedy" -> MovieGenre.COMEDY
        "Melodrama" -> MovieGenre.MELODRAMA
        "Music film" -> MovieGenre.MUSIC
        "Noir" -> MovieGenre.NOIR
        else -> MovieGenre.UNKNOWN
    }
}

fun getGenresStringList(): ArrayList<String> {
    val array = MovieGenre.values()
    val list: ArrayList<String> = ArrayList()
    for (genre in array) {
        list.add(getStringByGenre(genre))
    }
    return list
}

