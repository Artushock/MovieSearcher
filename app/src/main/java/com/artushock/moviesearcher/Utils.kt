package com.artushock.moviesearcher

fun getGenreString(genre : MovieGenre) : String {
    return when (genre){
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
    }
}

