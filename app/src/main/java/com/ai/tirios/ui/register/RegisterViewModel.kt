package com.ai.tirios.ui.register

import android.content.Context
import android.os.Bundle
import com.ai.tirios.dataclasses.BodySignUp
import com.ai.tirios.dataclasses.ResponseOtp
import com.ai.tirios.ui.signupchatbot.SignUpChatBotActivity
import com.ai.tirios.data.DataManager
import com.ai.tirios.data.api.CustomException
import com.ai.tirios.data.api.IApiListener
import com.ai.tirios.base.BaseViewModel

class RegisterViewModel internal constructor(dataManager: DataManager): BaseViewModel<RegisterMedium>(dataManager){

    fun onSubmitClick() = medium.onSubmitClick()

    fun registerUser(con: Context, url:String, tenantId:String, email:String, body: BodySignUp){
        medium.showProgressbar()
        dataManager.apiCall(object : IApiListener<ResponseOtp> {
            override fun onSuccessfulApi(response: ResponseOtp) {
                if (response.success) {
                    var bundle = Bundle()
                    bundle.putString("USER_ID",response.data.id)
                    bundle.putString("FIRST_NAME",response.data.firstName)
                    bundle.putString("LAST_NAME",response.data.lastName)
                    bundle.putString("MOBILE", body.mobile)
                    if (!tenantId.isNullOrEmpty()){
                        bundle.putString("TENANT_ID", tenantId)
                        bundle.putString("EMAIL", email)
                    }
                    medium.navigateTo(SignUpChatBotActivity::class.java, false, bundle)
                }else
                    medium.showToast(response.errors.get(0).description)
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

        }, dataManager.registerUser(url, body))
    }
}