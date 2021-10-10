package com.artushock.moviesearcher.model.states

import com.artushock.moviesearcher.model.dto.PersonDTO

sealed class PersonDetailState() {

    data class Success(
        val personDetail: PersonDTO
    ) : PersonDetailState()

    data class Error(
        val e: Throwable
    ) : PersonDetailState()

    object Loading : PersonDetailState()
}