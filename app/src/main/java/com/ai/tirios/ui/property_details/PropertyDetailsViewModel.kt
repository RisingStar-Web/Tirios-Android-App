package com.ai.tirios.ui.property_details

import androidx.lifecycle.MutableLiveData
import com.ai.tirios.dataclasses.*
import com.ai.tirios.utils.Constants
import com.ai.tirios.data.DataManager
import com.ai.tirios.data.api.CustomException
import com.ai.tirios.data.api.IApiListener
import com.ai.tirios.base.BaseViewModel

/**
 * Created by Maruthi Ram Yadav on 15-05-2021.
 */
class PropertyDetailsViewModel internal constructor(dataManager: DataManager)
    : BaseViewModel<PropertyDetailsMedium>(dataManager){

    var property_details: MutableLiveData<Property> = MutableLiveData()
    var update_tenant: MutableLiveData<Tenants> = MutableLiveData()

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

    fun addTenant() = medium.addTenant()

    fun editProperty() = medium.editProperty()

    fun editTenant(position: Int, id: Int) {
        medium.showProgressbar()
        dataManager.apiCall(object : IApiListener<Tenants> {
            override fun onSuccessfulApi(response: Tenants) {
                update_tenant.value = response
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

        }, dataManager.editISResiding(
            Constants.SharedPref.PROPERTY_BASE_URL +"tenant/",
            BodyIsReciding(id, false) )
        )
    }

    fun onBackArrowPressed() = medium.onBackArrowPressed()

}