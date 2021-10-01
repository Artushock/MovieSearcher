package com.artushock.moviesearcher.model.repositories

import com.artushock.moviesearcher.model.dto.MovieDetailDTO
import com.artushock.moviesearcher.model.dto.PersonDTO
import retrofit2.Callback

interface RepositoryPersonDetail {
    fun getActorsDetailByID(id: Int, callback: Callback<PersonDTO>)
}