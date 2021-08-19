package com.ai.tirios.ui.rented

import androidx.lifecycle.MutableLiveData
import com.ai.tirios.dataclasses.LandLordProperty
import com.ai.tirios.dataclasses.Property
import com.ai.tirios.utils.Constants
import com.ai.tirios.data.DataManager
import com.ai.tirios.data.api.CustomException
import com.ai.tirios.data.api.IApiListener
import com.ai.tirios.base.BaseViewModel

/**
 * Created by Rohit on 14-05-2021.
 */
class RentedViewModel internal constructor(dataManager: DataManager): BaseViewModel<RentedMedium>(dataManager){

    var properties_list: MutableLiveData<List<Property>> = MutableLiveData()
    var properties_array: MutableList<Property> = mutableListOf()

    fun addProperty() = medium.addProperty()

    fun getProperties(id: String) {
        medium.showProgressbar()
        dataManager.apiCall(object : IApiListener<LandLordProperty> {
            override fun onSuccessfulApi(response: LandLordProperty) {
                properties_array.clear()
                properties_array.addAll(response.TenantProperty)
                properties_list.value = response.TenantProperty
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

        }, dataManager.getProperties(Constants.SharedPref.PROPERTY_BASE_URL +"property/propertyList/"+id))
    }
}