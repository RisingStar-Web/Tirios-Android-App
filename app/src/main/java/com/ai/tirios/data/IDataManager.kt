package com.ai.tirios.data

import com.ai.tirios.data.api.IApiHelper
import com.ai.tirios.data.api.IApiListener
import com.ai.tirios.preference.IPreferencesHelper
import retrofit2.Call

interface IDataManager : IPreferencesHelper, IApiHelper {

    fun <T> apiCall(mApiListener: IApiListener<T>, tCall: Call<T>)

}