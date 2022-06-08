package com.example.genericapi.base

import android.app.Application
import com.example.genericapi.utils.Constants
import com.example.genericapi.network.ApiService
import com.example.genericapi.network.ConnectivityInterceptor
import com.example.genericapi.network.ConnectivityInterceptorImpl
import com.example.genericapi.network.ServiceGenerator
import com.example.genericapi.repository.Repository
import com.example.genericapi.repository.RepositoryImpl
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton

/**
 *
 * Created By Amir Fury on 19 May 2022
 *
 * **/
class GenericApp : Application(), KodeinAware {

    override val kodein = Kodein.lazy {
        import(androidXModule(this@GenericApp))

        bind<ConnectivityInterceptor>()  with singleton { ConnectivityInterceptorImpl(instance()) }
        bind() from singleton { ServiceGenerator.invoke<ApiService>(Constants.baseUrl,instance(),Constants.timeOut) }
        bind<Repository>() with singleton { RepositoryImpl(instance()) }
        bind() from singleton { GenericViewModel(instance()) }
    }
}