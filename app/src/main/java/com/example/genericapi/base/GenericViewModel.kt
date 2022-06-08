package com.example.genericapi.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.genericapi.network.Request
import com.example.genericapi.network.Resource
import com.example.genericapi.repository.Repository
import com.example.genericapi.response.NewsResponse
import kotlinx.coroutines.launch
import okhttp3.RequestBody

/**
 *
 * Created By Amir Fury on 19 May 2022
 *
 * **/
class GenericViewModel(private val repository: Repository) : ViewModel() {

    fun <ResponseType> get(apiUrl: String) : LiveData<Resource<ResponseType>> {
        val data = MutableLiveData<Resource<ResponseType>>()
        data.postValue(Resource.loading(null))
        viewModelScope.launch {
            val response = repository.get<ResponseType>(apiUrl)
            data.postValue(response)
        }
        return data
    }

    fun <ResponseType> post(
        apiUrl: String,
        request: Request
    ): LiveData<Resource<ResponseType>> {
        val data = MutableLiveData<Resource<ResponseType>>()
        data.postValue(Resource.loading(null))
        viewModelScope.launch { data.postValue(repository.post(apiUrl, request)) }
        return data
    }

    fun <ResponseType> postMultipart(
        apiUrl: String,
        requestBody: RequestBody
    ): LiveData<Resource<ResponseType>> {
        val data = MutableLiveData<Resource<ResponseType>>()
        data.postValue(Resource.loading(null))
        viewModelScope.launch { data.postValue(repository.multipart(apiUrl, requestBody)) }
        return data
    }

}