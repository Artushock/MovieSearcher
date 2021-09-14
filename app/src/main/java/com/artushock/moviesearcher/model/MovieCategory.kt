package com.artushock.moviesearcher.model

import java.net.URL

enum class MovieCategory(val uri: URL) {
    NEW(URL("https://api.themoviedb.org/3/movie/now_playing?api_key=051e149465ccfab1113dcf96b1096d37&language=ru-RU&page=1&region=ru")),
    POPULAR(URL("https://api.themoviedb.org/3/movie/popular?api_key=051e149465ccfab1113dcf96b1096d37&language=ru-RU&page=1"))
}

