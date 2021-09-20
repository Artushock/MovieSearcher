package com.artushock.moviesearcher.model.repositories

import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request

class RemoteDataSource {
    fun getRemoteData(uri: String, callback: Callback){
        val builder: Request.Builder = Request.Builder().apply {
            url(uri)
        }
        OkHttpClient().newCall(builder.build()).enqueue(callback)
    }
}