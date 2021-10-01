package com.artushock.moviesearcher.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.artushock.moviesearcher.model.dto.PersonDTO
import com.artushock.moviesearcher.model.repositories.RemoteDataSource
import com.artushock.moviesearcher.model.repositories.RepositoryPersonDetail
import com.artushock.moviesearcher.model.repositories.RepositoryPersonDetailImpl
import com.artushock.moviesearcher.model.states.PersonDetailState
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PersonDetailMapsViewModel(
    val personDetailLiveData: MutableLiveData<PersonDetailState> = MutableLiveData(),
    private val remoteRepository: RepositoryPersonDetail = RepositoryPersonDetailImpl(
        RemoteDataSource()
    )
) : ViewModel() {

    private val callback = object : Callback<PersonDTO> {
        override fun onResponse(call: Call<PersonDTO>, response: Response<PersonDTO>) {
            val serverResponse: PersonDTO? = response.body()
            personDetailLiveData.postValue(
                if (response.isSuccessful && serverResponse != null) {
                    PersonDetailState.Success(serverResponse)
                } else {
                    PersonDetailState.Error(Throwable("Error"))
                }
            )
        }

        override fun onFailure(call: Call<PersonDTO>, t: Throwable) {
            personDetailLiveData.postValue(PersonDetailState.Error(t))
        }

    }

    fun getPersonDetailFromServer(id: Int) {
        personDetailLiveData.value = PersonDetailState.Loading
        remoteRepository.getActorsDetailByID(id, callback)
    }
}