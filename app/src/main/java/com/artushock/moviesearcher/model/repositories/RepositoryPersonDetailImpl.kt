package com.artushock.moviesearcher.model.repositories

import com.artushock.moviesearcher.model.dto.PersonDTO
import retrofit2.Callback

class RepositoryPersonDetailImpl(
    val remoteDataSource: RemoteDataSource
) : RepositoryPersonDetail {
    override fun getActorsDetailByID(id: Int, callback: Callback<PersonDTO>) {
        remoteDataSource.getPersonByID(id, callback)
    }
}