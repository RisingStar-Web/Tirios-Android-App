package com.ai.tirios.ui.add_properties

import androidx.lifecycle.MutableLiveData
import com.ai.tirios.dataclasses.*
import com.ai.tirios.utils.Constants
import com.ai.tirios.data.DataManager
import com.ai.tirios.data.api.CustomException
import com.ai.tirios.data.api.IApiListener
import com.ai.tirios.base.BaseViewModel

/**
 * Created by Maruthi Ram Yadav on 20-05-2021.
 */

class AddPropertyViewModel internal constructor(dataManager: DataManager):
    BaseViewModel<AddPropertyMedium>(dataManager){

    var property : MutableLiveData<Property> = MutableLiveData()

    var property_upload : MutableLiveData<Property> = MutableLiveData()

    fun onBackArrowPressed() = medium.onBackArrowPressed()

    fun addProperty() = medium.addProperty()

    fun datePicker(from: String) = medium.datePicker(from)

    fun dispatchTakePictureIntent() = medium.imagePickerDialog()

    fun getProperty(id: String) {
        medium.showProgressbar()
        dataManager.apiCall(object : IApiListener<Property> {
            override fun onSuccessfulApi(response: Property) {
                medium.dismissProgressbar()
                property.value = response
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

        }, dataManager.getProperty(Constants.SharedPref.PROPERTY_BASE_URL+"property/"+id))
    }

    fun addProperty(bodyAddProperty: BodyAddProperty) {
        medium.showProgressbar()
        dataManager.apiCall(object : IApiListener<Property> {
            override fun onSuccessfulApi(response: Property) {
                medium.dismissProgressbar()
                medium.onBackArrowPressed()
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

        }, dataManager.addProperty(Constants.SharedPref.PROPERTY_BASE_URL+"property", bodyAddProperty))
    }

    fun editProperty(bodyEditProperty: BodyEditProperty) {
        medium.showProgressbar()
        dataManager.apiCall(object : IApiListener<Property> {
            override fun onSuccessfulApi(response: Property) {
                medium.dismissProgressbar()
                property_upload.value = response
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

        }, dataManager.editProperty(Constants.SharedPref.PROPERTY_BASE_URL+"property", bodyEditProperty))
    }

    fun propertyImageUpload(property_id: String, bodyPropertyImageUpload: BodyPropertyImageUpload) {
        medium.showProgressbar()
        dataManager.apiCall(object : IApiListener<ResponseImageUpload> {
            override fun onSuccessfulApi(response: ResponseImageUpload) {
                medium.dismissProgressbar()
                medium.onBackArrowPressed()
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

        }, dataManager.propertyImageUpload(Constants.SharedPref.PROPERTY_BASE_URL+"property/$property_id/upload", bodyPropertyImageUpload))
    }

}
