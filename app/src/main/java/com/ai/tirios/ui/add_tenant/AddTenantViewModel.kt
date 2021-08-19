package com.ai.tirios.ui.add_tenant

import androidx.lifecycle.MutableLiveData
import com.ai.tirios.dataclasses.*
import com.ai.tirios.utils.Constants.SharedPref.Companion.PROPERTY_BASE_URL
import com.google.gson.JsonObject
import com.ai.tirios.data.DataManager
import com.ai.tirios.data.api.CustomException
import com.ai.tirios.data.api.IApiListener
import com.ai.tirios.base.BaseViewModel

/**
 * Created by Maruthi Ram Yadav on 17-05-2021.
 */

class AddTenantViewModel internal constructor(dataManager: DataManager):
    BaseViewModel<AddTenantMidium>(dataManager){

    var tenant: MutableLiveData<Tenants> = MutableLiveData()

    var tenant_edit: MutableLiveData<Tenants> = MutableLiveData()

    fun onBackArrowPressed() = medium.onBackArrowPressed()

    fun adFamilyMember() = medium.adFamilyMember()

    fun removeFamilyMember() = medium.removeFamilyMember()

    fun adTenantClick() = medium.adTenant()

    fun dispatchTakePictureIntent() = medium.imagePickerDialog()

    fun adTenant(bodyAddTenant: JsonObject) {
        medium.showProgressbar()
        dataManager.apiCall(object : IApiListener<Tenants> {
            override fun onSuccessfulApi(response: Tenants) {
                medium.dismissProgressbar()
                tenant_edit.value = response
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

        }, dataManager.adTenant(PROPERTY_BASE_URL+"tenant", bodyAddTenant))
    }

    fun getTenant(id: String) {
        medium.showProgressbar()
        dataManager.apiCall(object : IApiListener<Tenants> {
            override fun onSuccessfulApi(response: Tenants) {
                medium.dismissProgressbar()
                tenant.value = response
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

        }, dataManager.getTenant(PROPERTY_BASE_URL+"tenant/"+id))
    }

    fun editTenant(jsonBodyEditTenant: JsonObject) {
        medium.showProgressbar()
        dataManager.apiCall(object : IApiListener<Tenants> {
            override fun onSuccessfulApi(response: Tenants) {
                medium.dismissProgressbar()
                tenant_edit.value = response
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

        }, dataManager.editTenant(PROPERTY_BASE_URL+"tenant", jsonBodyEditTenant))
    }

    fun uploadTenantImage(tenant_id:String, bodyTenantUpload: BodyTenantUpload) {
        medium.showProgressbar()
        dataManager.apiCall(object : IApiListener<ResponseTenantImageUpload> {
            override fun onSuccessfulApi(response: ResponseTenantImageUpload) {
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

        }, dataManager.uploadTenantImage(PROPERTY_BASE_URL+"tenant/$tenant_id/upload", bodyTenantUpload))
    }

    fun dataPicker(from: String) = medium.datePicker(from)

}
