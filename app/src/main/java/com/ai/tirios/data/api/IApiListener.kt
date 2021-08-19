package com.ai.tirios.data.api

import org.json.JSONException

interface IApiListener<T> {

    fun onSuccessfulApi(response: T)

    fun onFailureCodeApi(response: String)

    @Throws(JSONException::class)
    fun onFailureApi(e: CustomException)

    fun unAuth()
}
