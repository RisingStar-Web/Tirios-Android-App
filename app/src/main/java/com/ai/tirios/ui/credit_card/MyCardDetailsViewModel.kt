package com.ai.tirios.ui.credit_card

import androidx.lifecycle.MutableLiveData
import com.ai.tirios.base.BaseViewModel
import com.ai.tirios.data.DataManager
import com.ai.tirios.data.api.CustomException
import com.ai.tirios.data.api.IApiListener
import com.ai.tirios.dataclasses.ResponseBankAccounts
import com.ai.tirios.utils.Constants

class MyCardDetailsViewModel internal constructor(dataManager: DataManager) :
    BaseViewModel<MyCardDetailsMedium>(dataManager) {
    fun onBackArrowPressed() = medium.onBackArrowPressed()
    fun onAddCard() = medium.addAddCard()
    var bankAccounts: MutableLiveData<ResponseBankAccounts> = MutableLiveData()

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
}
