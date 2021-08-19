package com.ai.tirios.ui.select_payment

import androidx.lifecycle.MutableLiveData
import com.ai.tirios.utils.Constants
import com.ai.tirios.data.DataManager
import com.ai.tirios.data.api.CustomException
import com.ai.tirios.data.api.IApiListener
import com.ai.tirios.base.BaseViewModel
import com.ai.tirios.dataclasses.*

/**
 * Created by Rohit on 14-05-2021.
 */
class SelectPaymentViewModel internal constructor(dataManager: DataManager): BaseViewModel<SelectPaymentMedium>(dataManager){

    var bankAccounts: MutableLiveData<ResponseBankAccounts> = MutableLiveData()
    var responseBankAccounts: MutableLiveData<ResponseBankAccounts> = MutableLiveData()
    var responseBankPay : MutableLiveData<ResponseBankPay> = MutableLiveData()
    var responseUpdateInvoice : MutableLiveData<String> = MutableLiveData()
    var responseUpdateRent : MutableLiveData<String> = MutableLiveData()

    fun onBackPress()= medium.onBackPress()

    fun Pay()= medium.Pay()

    fun getLandLordBankAccounts(id: String) {
        medium.showProgressbar()
        dataManager.apiCall(object : IApiListener<ResponseBankAccounts> {
            override fun onSuccessfulApi(response: ResponseBankAccounts) {
                medium.dismissProgressbar()
                responseBankAccounts.value = response
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

        }, dataManager.getBankAccounts(Constants.SharedPref.IDENTITY_SWAGGER_BASE_URL +"Account/$id"))
    }

    fun getBankAccounts(id: String) {
        medium.showProgressbar()
        dataManager.apiCall(object : IApiListener<ResponseBankAccounts> {
            override fun onSuccessfulApi(response: ResponseBankAccounts) {
                medium.dismissProgressbar()
                bankAccounts.value = response
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

        }, dataManager.getBankAccounts(Constants.SharedPref.IDENTITY_SWAGGER_BASE_URL +"Account/$id"))
    }

    fun payWithBank(bodyPayWithBank: BodyPayWithBank){
        medium.showProgressbar()
        dataManager.apiCall(object : IApiListener<ResponseBankPay> {
            override fun onSuccessfulApi(response: ResponseBankPay) {
                medium.dismissProgressbar()
                responseBankPay.value = response
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

        }, dataManager.payWithBank(Constants.SharedPref.IDENTITY_SWAGGER_BASE_URL +"Transaction/BankAccount", bodyPayWithBank))
    }

    fun payWithCreditCard(bodyPayWithCard: BodyPayWithCard){
        medium.showProgressbar()
        dataManager.apiCall(object : IApiListener<ResponseBankPay> {
            override fun onSuccessfulApi(response: ResponseBankPay) {
                medium.dismissProgressbar()
                responseBankPay.value = response
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

        }, dataManager.payWithCreditCard(Constants.SharedPref.IDENTITY_SWAGGER_BASE_URL +"Transaction/CreditCard", bodyPayWithCard))
    }

    fun updateInvoiceStatus(bodyPayWithBank: UpdateInvoice){
        medium.showProgressbar()
        dataManager.apiCall(object : IApiListener<ResponseUpdateInvoice> {
            override fun onSuccessfulApi(response: ResponseUpdateInvoice) {
                medium.dismissProgressbar()
                responseUpdateInvoice.value = response.OrdeId
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

        }, dataManager.updateInvoice(Constants.SharedPref.PROPERTY_BASE_URL +"invoice", bodyPayWithBank))
    }

    fun updateRentStatus(bodyUpdateRent: UpdateRent){
        medium.showProgressbar()
        dataManager.apiCall(object : IApiListener<ResponseUpdateInvoice> {
            override fun onSuccessfulApi(response: ResponseUpdateInvoice) {
                medium.dismissProgressbar()
                responseUpdateRent.value = response.OrdeId
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

        }, dataManager.updateRent(Constants.SharedPref.PROPERTY_BASE_URL +"invoice/Rent", bodyUpdateRent))
    }

}