package com.ai.tirios.ui.owned

import androidx.lifecycle.MutableLiveData
import com.ai.tirios.dataclasses.LandLordProperty
import com.ai.tirios.dataclasses.Property
import com.ai.tirios.utils.Constants
import com.ai.tirios.data.DataManager
import com.ai.tirios.data.api.CustomException
import com.ai.tirios.data.api.IApiListener
import com.ai.tirios.base.BaseViewModel
import com.google.gson.JsonObject

/**
 * Created by Maruthi Ram Yadav on 14-05-2021.
 */

class OwnedViewModel internal constructor(dataManager: DataManager): BaseViewModel<OwnedMedium>(dataManager){

    var properties_list: MutableLiveData<List<Property>> = MutableLiveData()
    var properties_array: MutableList<Property> = mutableListOf()
    var Property : MutableLiveData<Property> = MutableLiveData()

    fun addProperty() = medium.addProperty()

    fun getProperties(id: String) {
        medium.showProgressbar()
        dataManager.apiCall(object : IApiListener<LandLordProperty> {
            override fun onSuccessfulApi(response: LandLordProperty) {
                properties_array.clear()
                properties_array.addAll(response.OwnedProperty)
                properties_list.value = response.OwnedProperty
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

    fun requestActivation(jsonObject: JsonObject) {
        medium.showProgressbar()
        dataManager.apiCall(object : IApiListener<Property> {
            override fun onSuccessfulApi(response: Property) {
                medium.dismissProgressbar()
                Property.value = response
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

        }, dataManager.requestActivation(Constants.SharedPref.PROPERTY_BASE_URL +"property", jsonObject))
    }

}
