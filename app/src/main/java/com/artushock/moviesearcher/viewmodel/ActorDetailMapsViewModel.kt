package com.artushock.moviesearcher.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.artushock.moviesearcher.model.dto.PersonDTO
import com.artushock.moviesearcher.model.repositories.*
import com.artushock.moviesearcher.model.states.PersonDetailState
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ActorDetailMapsViewModel(
    private val personDetailLiveData: MutableLiveData<PersonDetailState> = MutableLiveData(),
    private val remoteRepository: RepositoryPersonDetail = RepositoryPersonDetailImpl(
        RemoteDataSource()
    )
) : ViewModel() {

    private val callback = object : Callback<PersonDTO>{
        override fun onResponse(call: Call<PersonDTO>, response: Response<PersonDTO>) {
            TODO("Not yet implemented")
        }

        override fun onFailure(call: Call<PersonDTO>, t: Throwable) {
            TODO("Not yet implemented")
        }

    }

    fun getPersonDetailFromServer(id: Int) {
        personDetailLiveData.value = PersonDetailState.Loading
        remoteRepository.getActorsDetailByID(id, callback)
    }
}