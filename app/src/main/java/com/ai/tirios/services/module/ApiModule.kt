package com.ai.tirios.services.module

import android.content.Context
import com.ai.tirios.MainApplication
import com.ai.tirios.SharedStorage.SharedStorage
import com.ai.tirios.data.api.IApiHelper
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class ApiModule {

    @Provides
    @Singleton
    internal fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("http://ec2-3-229-26-172.")
            .addConverterFactory(GsonConverterFactory.create())
            .client(getOkHttpClient(MainApplication.context!!))
            .build()
    }

    private fun getOkHttpClient(context: Context): OkHttpClient {
        val logging = HttpLoggingInterceptor()
        val sharedStorage = SharedStorage(context).getInstance(context)
        logging.level = HttpLoggingInterceptor.Level.BODY

        val httpClient = OkHttpClient.Builder().addInterceptor(object : Interceptor {
            override fun intercept(chain: Interceptor.Chain): okhttp3.Response {
                var request = chain.request()
                if (!sharedStorage!!.gettoken().equals("")) {
                    request = chain.request().newBuilder()
                        .addHeader("Authorization", sharedStorage.gettoken().toString())
                        .build()
                }
                return chain.proceed(request)
            }
        }).connectTimeout(6, TimeUnit.MINUTES)
            .writeTimeout(6, TimeUnit.MINUTES)
            .readTimeout(6, TimeUnit.MINUTES)
            .callTimeout(6, TimeUnit.MINUTES)
        httpClient.interceptors().add(logging)
        return httpClient.build()
    }

    @Singleton
    @Provides
    internal fun provideRetrofitService(retrofit: Retrofit) =
        retrofit.create(IApiHelper::class.java)

    /*@Singleton
    @Provides
    internal fun identityRetrofitService(retrofit: Retrofit) =
        retrofit.create(IApiHelper::class.java)

    @Singleton
    @Provides
    internal fun propertyRetrofitService(retrofit: Retrofit) =
        retrofit.create(IApiHelper::class.java)*/
}
