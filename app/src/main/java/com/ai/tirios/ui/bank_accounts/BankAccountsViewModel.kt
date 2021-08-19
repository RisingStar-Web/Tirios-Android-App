package com.ai.tirios.ui.bank_accounts

import android.content.Context
import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import com.ai.tirios.ui.signupchatbot.SignUpChatBotActivity
import com.ai.tirios.data.DataManager
import com.ai.tirios.data.api.CustomException
import com.ai.tirios.data.api.IApiListener
import com.ai.tirios.base.BaseViewModel
import com.ai.tirios.dataclasses.*
import com.ai.tirios.ui.add_bank_accounts.AddBankAccountsActivity
import com.ai.tirios.utils.Constants
import com.ai.tirios.utils.Constants.SharedPref.Companion.IDENTITY_SWAGGER_BASE_URL
import com.google.gson.JsonObject

class BankAccountsViewModel internal constructor(dataManager: DataManager): BaseViewModel<BankAccountsMedium>(dataManager){

    var bankAccounts: MutableLiveData<ResponseBankAccounts> = MutableLiveData()
    var landLordProperty: MutableLiveData<LandLordProperty> = MutableLiveData()

    fun addAccountClick() = medium.addAccountClick()

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

        }, dataManager.getBankAccounts(IDENTITY_SWAGGER_BASE_URL+"Account/$id"))
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

}
