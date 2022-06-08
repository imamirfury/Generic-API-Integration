package com.example.genericapi.repository

import com.example.genericapi.network.Request
import com.example.genericapi.network.Resource
import okhttp3.RequestBody
import okhttp3.ResponseBody

/**
 *
 * Created By Amir Fury on 19 May 2022
 *
 * **/
interface Repository {

    suspend fun<ResponseType>  get(apiUrl: String): Resource<ResponseType>

    suspend fun <ResponseType> post(
        apiUrl: String,
        request: Request
    ): Resource<ResponseType>

    suspend fun <ResponseType> multipart(apiUrl: String,requestBody: RequestBody) : Resource<ResponseType>
}