package com.ai.tirios.ui.add_bank_accounts

import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import com.ai.tirios.data.DataManager
import com.ai.tirios.data.api.CustomException
import com.ai.tirios.data.api.IApiListener
import com.ai.tirios.base.BaseViewModel
import com.ai.tirios.dataclasses.*
import com.ai.tirios.ui.setting.tnc.TermsAndPolicyActivity
import com.ai.tirios.utils.Constants

class AddBankAccountsViewModel internal constructor(dataManager: DataManager): BaseViewModel<AddBankAccountsMedium>(dataManager){

    var merchant: MutableLiveData<ResponseMerchant> = MutableLiveData()
    var customer: MutableLiveData<ResponseAddCustomer> = MutableLiveData()
    var bankAccountTokenization: MutableLiveData<BankAccountTokenization> = MutableLiveData()

    fun addAccount() = medium.addAccount()

    fun viewContent(fileName: String, title: String) {
        val bundle = Bundle()
        bundle.putString("FILE_PATH", fileName)
        bundle.putString("FILE_TITLE", title)
        medium.navigateTo(TermsAndPolicyActivity::class.java, false, bundle)
    }

    fun addMerchant(body: BodyAddMerchant) {
        medium.showProgressbar()
        dataManager.apiCall(object : IApiListener<ResponseMerchant> {
            override fun onSuccessfulApi(response: ResponseMerchant) {
                medium.dismissProgressbar()
                merchant.value = response
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

        }, dataManager.addMerchant(Constants.SharedPref.IDENTITY_SWAGGER_BASE_URL +"Merchant", body))
    }

    fun addCustomer(body: BodyAddCustomer) {
        medium.showProgressbar()
        dataManager.apiCall(object : IApiListener<ResponseAddCustomer> {
            override fun onSuccessfulApi(response: ResponseAddCustomer) {
                medium.dismissProgressbar()
                customer.value = response
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

        }, dataManager.addCustomer(Constants.SharedPref.IDENTITY_SWAGGER_BASE_URL +"Customer", body))
    }

    fun getBankAccountToken(body: BodyBankAccountTokenization) {
        medium.showProgressbar()
        dataManager.apiCall(object : IApiListener<BankAccountTokenization> {
            override fun onSuccessfulApi(response: BankAccountTokenization) {
                medium.dismissProgressbar()
                bankAccountTokenization.value = response
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

        }, dataManager.getBankAccountToken(Constants.SharedPref.IDENTITY_SWAGGER_BASE_URL +"BankAccountTokenization", body))
    }

}
