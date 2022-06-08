package com.example.genericapi.repository

import com.example.genericapi.network.ApiService
import com.example.genericapi.network.Request
import com.example.genericapi.network.Resource
import com.example.genericapi.network.SafeApiRequest
import com.example.genericapi.utils.asJsonObject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.RequestBody
import okhttp3.ResponseBody

/**
 *
 * Created By Amir Fury on 19 May 2022
 *
 * **/
class RepositoryImpl(private val apiService: ApiService) : Repository, SafeApiRequest() {


    override suspend fun <ResponseType> get(apiUrl: String): Resource<ResponseType> {
        return withContext(Dispatchers.IO) {
            try {
                apiCall { apiService.get(apiUrl) }
            } catch (e: Exception) {
                Resource.error(e.message, null)
            }
        }
    }

    override suspend fun <ResponseType> post(
        apiUrl: String,
        request: Request
    ): Resource<ResponseType> {
        return withContext(Dispatchers.IO) {
            try {
                apiCall { apiService.post(apiUrl, request.asJsonObject()) }
            } catch (e: Exception) {
                Resource.error(e.message, null)
            }
        }
    }

    override suspend fun <ResponseType> multipart(
        apiUrl: String,
        requestBody: RequestBody
    ): Resource<ResponseType> {
        return withContext(Dispatchers.IO){
            try {
                apiCall { apiService.postMultipart(apiUrl,requestBody) }
            }catch (e : Exception){
                Resource.error(e.message,null)
            }
        }
    }


}