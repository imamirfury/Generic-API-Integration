package com.example.genericapi.base

import android.os.Bundle
import android.view.LayoutInflater
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.FragmentActivity
import com.example.genericapi.R
import com.example.genericapi.databinding.ActivityBaseBinding
import com.example.genericapi.network.Request
import com.example.genericapi.network.Status
import com.example.genericapi.utils.*
import okhttp3.RequestBody
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance

/**
 *
 * Created By Amir Fury on 19 May 2022
 *
 * **/

abstract class BaseActivity<B : ViewDataBinding> : FragmentActivity(), KodeinAware {

    override val kodein: Kodein by kodein()

    protected val viewModel by instance<GenericViewModel>()

    val baseBinding: ActivityBaseBinding by lazy {
        DataBindingUtil.setContentView(
            this,
            R.layout.activity_base
        )
    }

    protected val binding: B by lazy {
        DataBindingUtil.inflate(
            LayoutInflater.from(this),
            layoutRes,
            null,
            false
        )
    }

    @get:LayoutRes
    protected abstract val layoutRes: Int

    protected abstract fun onUiReady(instanceState: Bundle?, binding: B)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        baseBinding.activityFrame.addView(binding.root)
        onUiReady(savedInstanceState, binding)
        setClicks()
    }

    private fun setClicks() {
        baseBinding.apply {
            retryButton.setOnClickListener { retry() }
        }
    }


    protected inline fun <reified T> getRequest(apiUrl: String) {
        viewModel.get<T>(apiUrl).observe(this) {
            when (it?.status) {
                Status.LOADING -> {
                    showContent()
                    baseBinding.progressBar.show()
                }
                Status.SUCCESS -> {
                    baseBinding.progressBar.hide()
                    val data = it.data.convertToResponse<T>()
                    showData(data)
                }
                Status.ERROR -> {
                    baseBinding.progressBar.hide()
                    onError(it.message.toString())
                }
                else -> {

                }
            }
        }
    }


    protected inline fun <reified T> postRequest(apiUrl: String, request: Request) {
        viewModel.post<T>(apiUrl, request).observe(this) {
            when (it?.status) {
                Status.LOADING -> {
                    showContent()
                    toast(string(R.string.loading))
                }
                Status.SUCCESS -> {
                    val data = it.data.convertToResponse<T>()
                    showData(data)
                }
                Status.ERROR -> {
                    onError(it.message.toString())
                }
                else -> {

                }
            }
        }
    }

    protected inline fun <reified T> multipartRequest(apiUrl: String, requestBody: RequestBody) {
        viewModel.postMultipart<T>(apiUrl, requestBody).observe(this) {
            when (it?.status) {
                Status.LOADING -> {
                    showContent()
                    toast(string(R.string.loading))
                }
                Status.SUCCESS -> {
                    val data = it.data.convertToResponse<T>()
                    showData(data)
                }
                Status.ERROR -> {
                    onError(it.message.toString())
                }
                else -> {

                }
            }
        }
    }

    open fun <T> showData(data: T) {}

    open fun retry() {}

    open fun showContent() {
        baseBinding.apply {
            activityFrame.show()
            errorMsg.hide()
        }
    }

    open fun onError(message: String) {
        baseBinding.apply {
            activityFrame.hide()
            errorMsg.show()
            errorMsg.text = message
        }
    }

}