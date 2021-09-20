package com.artushock.moviesearcher.model.repositories

import okhttp3.Callback

interface RepositoryMovieDetail {
    fun getMovieDataFromServer(uri: String, callback: Callback)
}