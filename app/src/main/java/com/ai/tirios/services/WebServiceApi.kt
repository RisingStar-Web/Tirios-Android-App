package com.ai.tirios.services

import com.ai.tirios.dataclasses.BodyOtp
import com.ai.tirios.dataclasses.ResponseOtp
import com.google.gson.GsonBuilder
import io.reactivex.Observable
import okhttp3.OkHttpClient
import okhttp3.Protocol
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Url
import java.lang.reflect.Modifier
import java.util.concurrent.TimeUnit

interface WebServiceApi {

    companion object {
        fun create(basUrl: String): WebServiceApi {
            val interceptor = HttpLoggingInterceptor()
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
            val client = OkHttpClient.Builder().addInterceptor(interceptor)
                .protocols(listOf(Protocol.HTTP_1_1))
                .connectTimeout(2, TimeUnit.MINUTES)
                .readTimeout(2, TimeUnit.MINUTES).build()
            val gson = GsonBuilder()
                .excludeFieldsWithModifiers(Modifier.TRANSIENT)
                .disableHtmlEscaping()
                .setLenient()
                .create()
            val retrofit = Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(client)
                .baseUrl(basUrl)
                .build()

            return retrofit.create(WebServiceApi::class.java)
        }
    }

    @POST
    fun generateOtp(@Url url: String): Observable<ResponseOtp>

    @POST
    fun resendOtp(@Url url: String): Observable<ResponseOtp>

    @POST("/api/auth/otpValidate")
    fun validateOtp(@Header("Content-Type") type: String, @Body body: BodyOtp): Observable<ResponseOtp>

}