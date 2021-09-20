package com.artushock.moviesearcher.model.repositories

import okhttp3.Callback

class RepositoryMovieDetailImpl(
    val remoteDataSource: RemoteDataSource
) : RepositoryMovieDetail {

    override fun getMovieDataFromServer(uri: String, callback: Callback) {
        remoteDataSource.getRemoteData(uri, callback)
    }
}