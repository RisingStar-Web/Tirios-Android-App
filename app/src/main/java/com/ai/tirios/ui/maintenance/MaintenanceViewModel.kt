package com.ai.tirios.ui.maintenance

import androidx.lifecycle.MutableLiveData
import com.ai.tirios.base.BaseViewModel
import com.ai.tirios.dataclasses.LandLordProperty
import com.ai.tirios.dataclasses.ResponseMaintainance
import com.ai.tirios.utils.Constants
import com.ai.tirios.data.DataManager
import com.ai.tirios.data.api.CustomException
import com.ai.tirios.data.api.IApiListener
import com.ai.tirios.dataclasses.Property
import com.ai.tirios.dataclasses.ResponseViewInvoice

/**
 * Created by Maruthi Ram Yadav on 13-05-2021.
 */
class MaintenanceViewModel internal constructor(dataManager: DataManager): BaseViewModel<MaintenanceMedium>(dataManager) {

    var landLordProperty: MutableLiveData<LandLordProperty> = MutableLiveData()

    var maintainance: MutableLiveData<List<ResponseMaintainance>> = MutableLiveData()

    var invoice : MutableLiveData<ResponseViewInvoice> = MutableLiveData()

    fun newRequest() = medium.newRequest()

    fun callServiceForInvoice(id : Int){
        medium.showProgressbar()
        dataManager.apiCall(object : IApiListener<ResponseViewInvoice> {
            override fun onSuccessfulApi(response: ResponseViewInvoice) {
                invoice.value = response
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
        }, dataManager.getInvoiceDocument(Constants.SharedPref.PROPERTY_BASE_URL +"invoice/document/"+id))
    }

    fun getProperties(id: String) {
        medium.showProgressbar()
        dataManager.apiCall(object : IApiListener<LandLordProperty> {
            override fun onSuccessfulApi(response: LandLordProperty) {
                landLordProperty.value = response
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

    fun getMaintainance(property_id: Int) {
        medium.showProgressbar()
        dataManager.apiCall(object : IApiListener<List<ResponseMaintainance>> {
            override fun onSuccessfulApi(response: List<ResponseMaintainance>) {
                maintainance.value = response
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

        }, dataManager.getMaintainance(Constants.SharedPref.PROPERTY_BASE_URL +"maintenance/$property_id/getbypropertyid"))
    }

}
