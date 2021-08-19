package com.ai.tirios.ui.credit_card.add_card

import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import com.ai.tirios.base.BaseViewModel
import com.ai.tirios.data.DataManager
import com.ai.tirios.data.api.CustomException
import com.ai.tirios.data.api.IApiListener
import com.ai.tirios.dataclasses.BodyAddCreditCard
import com.ai.tirios.dataclasses.ResponseBankAccounts
import com.ai.tirios.dataclasses.ResponseCreditCard
import com.ai.tirios.ui.setting.tnc.TermsAndPolicyActivity
import com.ai.tirios.utils.Constants

class AddCardViewModel internal constructor(dataManager: DataManager): BaseViewModel<AddCardMedium>(dataManager) {

    var bankAccounts: MutableLiveData<ResponseBankAccounts> = MutableLiveData()

    fun onBackArrowPressed() = medium.onBackArrowPressed()

    fun addCreditCard() = medium.addCreditCard()

    fun viewTermsContent(fileName: String, title: String) {
        val bundle = Bundle()
        bundle.putString("FILE_PATH", fileName)
        bundle.putString("FILE_TITLE", title)
        medium.navigateTo(TermsAndPolicyActivity::class.java, false, bundle)
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

    fun addCardAPI(bodyAddCreditCard: BodyAddCreditCard) {
        medium.showProgressbar()
        dataManager.apiCall(object : IApiListener<ResponseCreditCard> {
            override fun onSuccessfulApi(response: ResponseCreditCard) {
                medium.dismissProgressbar()
                medium.finishScreen()
            }

            override fun onFailureCodeApi(response: String) {
                medium.dismissProgressbar()
                medium.showAlert(response)
            }

            override fun onFailureApi(e: CustomException) {
                medium.dismissProgressbar()
                medium.sesionExpired()
            }

            override fun unAuth() {
                medium.dismissProgressbar()
            }

        }, dataManager.addCreditCard(Constants.SharedPref.IDENTITY_SWAGGER_BASE_URL +"CreditCardTokenization", bodyAddCreditCard))
    }
}