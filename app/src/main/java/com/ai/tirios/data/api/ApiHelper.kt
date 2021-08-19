package com.ai.tirios.data.api

import com.ai.tirios.utils.Constants.InternalHttpCode.Companion.INTERNAL_SERVER_ERROR
import com.ai.tirios.utils.Constants.InternalHttpCode.Companion.LOCKED
import com.ai.tirios.utils.Constants.InternalHttpCode.Companion.SERVICE_UNAVAILABLE
import com.ai.tirios.utils.Constants.InternalHttpCode.Companion.TIMEOUT_ERROR
import com.ai.tirios.utils.Constants.InternalHttpCode.Companion.TOO_MANY_REQUESTS
import com.ai.tirios.utils.Constants.InternalHttpCode.Companion.UNAUTHORIZED_ACCESS
import com.ai.tirios.utils.Constants.InternalHttpCode.Companion.VIDEO_UPLOAD_SIZE_FAIL
import org.json.JSONException
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import java.net.SocketTimeoutException
import javax.inject.Inject

abstract class ApiHelper<T> : IApiListener<T>, Callback<T> {

    var mRetrofit: Retrofit? = null
        @Inject set

    override fun onResponse(call: Call<T>, response: Response<T>) {
        try {
            when {
                response.isSuccessful && response.body() != null -> response.body()
                    ?.let { onSuccessfulApi(it) }
                else -> when (response.code()) {
                    UNAUTHORIZED_ACCESS -> {
                        onFailureApi(CustomException(response.code(), "Session expired, Please login"))
                    }
                    INTERNAL_SERVER_ERROR -> onFailureCodeApi("The server is down and it will be resolved soon. Inconvenience is regretted.")
                    TIMEOUT_ERROR -> onFailureCodeApi("OOPS!!! Please Try again Later")
                    SERVICE_UNAVAILABLE -> onFailureCodeApi("We are busy updating the Website for you and will be back shortly")
                    TOO_MANY_REQUESTS ->onFailureCodeApi("Too Many Requests")
                    LOCKED ->onFailureCodeApi("Locked")
                    VIDEO_UPLOAD_SIZE_FAIL ->onFailureCodeApi("Video size should be less than 10MB.")

                    else -> onFailureCodeApi(response.errorBody()?.string() ?: "unknown error")
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onFailure(call: Call<T>, t: Throwable) = try {
        when (t) {
            is SocketTimeoutException -> onFailureCodeApi(
                "OOPS!!! Please Try again Later")
            else -> onFailureCodeApi("OOPS!!! Please Try again Later")
        }
    } catch (e: JSONException) {
        e.printStackTrace()
    }
}
