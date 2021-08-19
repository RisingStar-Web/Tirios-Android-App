package com.ai.tirios.ui.tenant_property_details

import androidx.lifecycle.MutableLiveData
import com.ai.tirios.dataclasses.Property
import com.ai.tirios.utils.Constants
import com.ai.tirios.data.DataManager
import com.ai.tirios.data.api.CustomException
import com.ai.tirios.data.api.IApiListener
import com.ai.tirios.base.BaseViewModel
import com.ai.tirios.dataclasses.ResponseTenantImageUpload
import com.google.gson.JsonObject

/**
 * Created by Maruthi Ram Yadav on 27-05-2021.
 */

class TenantDetailsViewModel internal constructor(dataManager: DataManager)
    : BaseViewModel<TenantDetailsMedium>(dataManager) {

    fun payRent() = medium.payRent()
    fun onBackPress() = medium.onBackPress()

    var property_details: MutableLiveData<Property> = MutableLiveData()
    var lease_agrement: MutableLiveData<ResponseTenantImageUpload> = MutableLiveData()

    fun getPropertyDetails(id: String) {
        medium.showProgressbar()
        dataManager.apiCall(object : IApiListener<Property> {
            override fun onSuccessfulApi(response: Property) {
                property_details.value = response
                medium.dismissProgressbar()
            }

            override fun onFailureCodeApi(response: String) {
                medium.dismissProgressbar()
                medium.showToast(response)
            }

            override fun onFailureApi(e: CustomException) {
                medium.dismissProgressbar()
                medium.sesionExpired()
            }

            override fun unAuth() {
                medium.dismissProgressbar()
            }

        }, dataManager.getPropertyDetails(Constants.SharedPref.PROPERTY_BASE_URL +"property/"+id))
    }

    fun getTenantAgrement(id: String, bodyTenantAgrement: JsonObject) {
        medium.showProgressbar()
        dataManager.apiCall(object : IApiListener<ResponseTenantImageUpload> {
            override fun onSuccessfulApi(response: ResponseTenantImageUpload) {
                lease_agrement.value = response
                medium.dismissProgressbar()
            }

            override fun onFailureCodeApi(response: String) {
                medium.dismissProgressbar()
                medium.showToast(response)
            }

            override fun onFailureApi(e: CustomException) {
                medium.dismissProgressbar()
                medium.sesionExpired()
            }

            override fun unAuth() {
                medium.dismissProgressbar()
            }

        }, dataManager.getTenantAgrement(Constants.SharedPref.PROPERTY_BASE_URL +"tenant/"+id, bodyTenantAgrement))
    }
}