@file:Suppress("UNCHECKED_CAST")

package com.example.genericapi.utils

import android.content.Context
import android.util.Log
import android.view.View
import android.widget.Toast
import com.example.genericapi.network.Request
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import com.google.gson.internal.LinkedTreeMap
import com.google.gson.reflect.TypeToken
import org.json.JSONObject

/**
 *
 * Created By Amir Fury on 19 May 2022
 *
 * **/

inline fun <reified T> Gson.fromJson(json: String) =
    this.fromJson<T>(json, object : TypeToken<T>() {}.type)

inline fun <reified T : Any> T.json(): String = Gson().toJson(this, T::class.java)

inline fun <reified T> T?.convertToResponse(): T {
    val jsonObject = JSONObject(this as LinkedTreeMap<String, Any>)
    return Gson().fromJson(jsonObject.toString(), T::class.java)
}

fun Request.asJsonObject(): JsonObject {
    return JsonParser.parseString(this.json()).asJsonObject
}

fun Context.toast(message: String?) =
    Toast.makeText(this, message.toString(), Toast.LENGTH_SHORT).show()

fun Context.string(resId: Int): String = resources.getString(resId)

fun Any.printLog(tag: String, message: String?) = Log.d(tag, message.toString())

fun View.show() {
    visibility = View.VISIBLE
}

fun View.hide() {
    visibility = View.GONE
}