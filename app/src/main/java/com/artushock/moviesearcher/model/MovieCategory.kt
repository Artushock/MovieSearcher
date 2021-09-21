package com.artushock.moviesearcher.model

import com.artushock.moviesearcher.BuildConfig
import java.net.URL

enum class MovieCategory(val uri: URL) {
    TOP_RATED(URL("https://api.themoviedb.org/3/movie/top_rated?api_key=${BuildConfig.THEMOVIEDB_API_KEY}&language=ru-RU&page=1")),
    NOW_PLAYING(URL("https://api.themoviedb.org/3/movie/now_playing?api_key=${BuildConfig.THEMOVIEDB_API_KEY}&language=ru-RU&page=1")),
    POPULAR(URL("https://api.themoviedb.org/3/movie/popular?api_key=${BuildConfig.THEMOVIEDB_API_KEY}&language=ru-RU&page=1"))
}

