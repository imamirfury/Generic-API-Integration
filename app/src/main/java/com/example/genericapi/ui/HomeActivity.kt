package com.example.genericapi.ui

import android.os.Bundle
import com.example.genericapi.R
import com.example.genericapi.base.BaseActivity
import com.example.genericapi.databinding.ActivityHomeBinding
import com.example.genericapi.response.NewsResponse
import com.example.genericapi.utils.Constants

/**
 *
 * Created By Amir Fury on 19 May 2022
 *
 * **/
class HomeActivity : BaseActivity<ActivityHomeBinding>() {


    override val layoutRes: Int get() = R.layout.activity_home

    override fun onUiReady(instanceState: Bundle?, binding: ActivityHomeBinding) {
        fetchData()
    }

    private fun fetchData() {
        getRequest<NewsResponse>(Constants.apiUrl.plus(Constants.newsApiKey))
    }

    override fun <T> showData(data: T) {
        super.showData(data)
        when (data) {
            is NewsResponse -> {
                binding.tv.text = data.articles.toString()
            }
        }
    }

    override fun retry() {
        super.retry()
        fetchData()
    }
}